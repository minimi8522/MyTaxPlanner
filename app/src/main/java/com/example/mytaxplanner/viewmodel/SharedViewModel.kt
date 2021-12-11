package com.example.mytaxplanner.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.mytaxplanner.model.DeductData
import com.example.mytaxplanner.model.DeductType
import com.example.mytaxplanner.model.TypeDeductList
import com.example.mytaxplanner.model.entity.DeductDataEntity
import com.example.mytaxplanner.model.entity.IncomeDataEntity
import com.example.mytaxplanner.repository.TaxPlannerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import kotlin.math.abs

class SharedViewModel(val context: Application) : AndroidViewModel(context) {

    companion object {
        const val LV0 = 0
        const val LV1 = 0.05
        const val LV2 = 0.10
        const val LV3 = 0.15
        const val LV4 = 0.20
        const val LV5 = 0.25
        const val LV6 = 0.30
        const val LV7 = 0.35
    }

//    private var _incomeList = MutableLiveData<MutableList<IncomeData>>()
//    val incomeList : LiveData<MutableList<IncomeData>> = _incomeList


//    val deductList : LiveData<MutableList<DeductData>> = _deductList

    val db = TaxPlannerDatabase.getInstance(context).taxPlannerDao
    var incomeList: LiveData<List<IncomeDataEntity>>

    var deductList: LiveData<List<DeductDataEntity>>

    var calculateTaxIncome: MediatorLiveData<Double> = MediatorLiveData()

    var insuranceMax = 100000.0
    var insuranceDiff = 0.0
    var fundsMax = 500000.0

    init {
        incomeList = db.getIncome()
        deductList = db.getDeduct()

        calculateTaxIncome.addSource(incomeList) {
            calculateTaxIncome.value = calculateTaxIncome()

        }

        calculateTaxIncome.addSource(deductList) {
            calculateTaxIncome.value = calculateTaxIncome()
        }
    }

    fun addIncomeData(type: Int, income: Double, incomeVAT: Double) {
        // _incomeList.value?.add(IncomeData(income,incomeVAT))
        viewModelScope.launch(Dispatchers.IO) {

            db.insertIncomeData(IncomeDataEntity(0, type, income, incomeVAT))
        }
    }

    fun addDeductData(deductType: Int, deductVal: Double, deductMax: Double) {
//        _deductList.value?.add(DeductData(deductType,deductVal))

        var data = DeductData(deductType, deductVal , deductMax)
//        if(deductType==9 || deductType==10){
//            if(insuranceMax>0){
//                insuranceMax -= deductVal
//            }else data.deduction = 0.0
//        }
        when(deductType){
            9,11->{
                if(insuranceMax>0){
                    insuranceMax -= deductVal
                }else data.deduction = 0.0
            }
            14,15,16,17,18,19 ->{
                if(fundsMax>0){
                    fundsMax -= deductVal
                }else data.deduction = 0.0
            }
        }
        viewModelScope.launch(Dispatchers.IO) {

            db.insertDeductData(
                DeductDataEntity(
                    0,
                    data.deductionType,
                    data.deduction,
                    data.deductionMax
                )
            )
        }
    }

    fun removeIncomeAt(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            db.deleteIncome(index)
        }
//        if (!_incomeList.value.isNullOrEmpty()) {
//            val oldValue = _incomeList.value
//            oldValue?.removeAt(index).also { _incomeList.value = oldValue!! }
//        } else {
//            _incomeList.value = mutableListOf()
//        }
    }

    fun removeDeductAt(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            var deductData = db.getDeductByIndex(index)
            when(deductData.deductType){
                9,11 ->{
                    insuranceMax += deductData.deduction
                    if (insuranceMax>100000.0){
                        insuranceMax = 100000.0
                    }
                }
//                10 ->{
//                    if(insuranceDiff>0) {
//                        insuranceMax += insuranceDiff
//                        if (insuranceMax > 100000.0) {
//                            insuranceMax = 100000.0
//                        }
//                    }
//                }
                14,15,16,17,18,19 ->{
                    fundsMax += deductData.deduction
                    if (fundsMax>500000.0){
                        fundsMax = 500000.0
                    }
                }
            }
            db.deleteDeduct(index)

        }
//        if (!_incomeList.value.isNullOrEmpty()) {
//            val oldValue = _incomeList.value
//            oldValue?.removeAt(index).also { _incomeList.value = oldValue!! }
//        } else {
//            _incomeList.value = mutableListOf()
//        }
    }

    fun calculateVat(): Double {
        val income = calculateIncome(incomeList.value)
        val taxPaid = calculateTaxPaid(incomeList.value)
        val deduct = calculateDeduct(deductList.value)
        if (deduct > income) {
            return -taxPaid
        }
        var vat = 0.0

        if (income == 0.0 && deduct > 0.0) {
            return 0.0
        }
        var incomeCal = income - deduct
        when (incomeCal) {
            in 0.0..150000.0 -> {

            }
            in 150001.0..300000.0 -> {
                vat += (incomeCal - 150000.0) * LV1
                vat += 0.0
            }
            in 300001.0..500000.0 -> {
                vat += (incomeCal - 300000.0) * LV2
                vat += 7500.0
            }
            in 500001.0..750000.0 -> {
                vat += (incomeCal - 500000.0) * LV3
                vat += 27500.0
            }
            in 750001.0..1000000.0 -> {
                vat += (incomeCal - 750000.0) * LV4
                vat += 65000.0
            }
            in 1000001.0..2000000.0 -> {
                vat += (incomeCal - 1000000.0) * LV5
                vat += 115000.0
            }
            in 2000001.0..5000000.0 -> {
                vat += (incomeCal - 2000000.0) * LV6
                vat += 365000.0
            }
            else -> {
                vat += (incomeCal - 5000000.0) * LV7
                vat += 1265000.0
            }
        }
        return vat - taxPaid
    }


    fun calculateIncome(list: List<IncomeDataEntity>?): Double {
        return if (list != null) {
            var sum = 0.0
            var type_1_2 = 0.0
            var type3 = 0.0

            list.forEach {
                when (it.type) {

                    //ประเภท 1 และ 2
                    0, 1 -> {
                        type_1_2 += it.income
                    }

                    //ประเภท 3
                    2, 3 -> {
                        type3 += it.income
                    }

                    //ประเภท 5
                    5, 8 -> {
                        sum += it.income * 0.3
                    }
                    6, 10 -> {
                        sum += it.income * 0.2
                    }
                    7 -> {
                        sum += it.income * 0.15
                    }
                    9 -> {
                        sum += it.income * 0.1
                    }

                    //ประเภท 6
                    11 -> {
                        sum += it.income * 0.6
                    }
                    12 -> {
                        sum += it.income * 0.3
                    }

                    //ประเภท 7
                    13 -> {
                        sum += it.income * 0.6
                    }
                    //ประเภท 8
                    15 -> {
                        //TODO เงื่อนไขนอกเมือง
                        sum += it.income * 0.5
                    }
                    16 -> {
                        //TODO เงื่อนไขตามปีที่ถือ
                        sum += it.income * 0.3
                    }
                    17 -> {
                        var modelTaxPrice = 0.0
                        if (it.income <= 300000.0) {
                            modelTaxPrice += it.income * 0.6
                        } else {
                            var calPrice = it.income - 300000
                            modelTaxPrice += (calPrice * 0.4) + (300000 * 0.6)
                            if (modelTaxPrice > 600000.0) {
                                modelTaxPrice = 600000.0
                            }
                        }
                        sum += modelTaxPrice
                    }
                    18, 19 -> {
                        sum += it.income * 0.6
                    }
                    20 -> {
                        //Todo ถ้าเช็คหลักฐานได้ควรมีหักค่าใช้่จ่าย
                        sum += it.income
                    }

                    // Type4,14
                    else -> {
                        sum += it.income
                    }
                }
            }
            if (type_1_2 / 2.0 > 100000.0) {
                type_1_2 -= 100000.0
            } else {
                type_1_2 /= 2.0
            }
            if (type3 / 2.0 > 100000.0) {
                type3 -= 100000.0
            } else {
                type3 /= 2.0
            }
            sum + type_1_2 + type3

        } else {
            0.0
        }
    }

    fun calculateTaxPaid(list: List<IncomeDataEntity>?): Double {
        return if (list != null) {
            var sum = 0.0


            list.forEach {
                sum += it.incomeVAT
            }
            sum
        } else {
            0.0
        }
    }

    fun calculateDeduct(list: List<DeductDataEntity>?): Double {
        return if (list != null) {
            var sum = 0.0
            var family = 0.0
            var fund = 0.0
            var insurance = 0.0

            list.forEach {
                when (it.deductType) {
                    //ครอบครัว
                    0, 1, 2, 3, 4, 5, 6, 7 -> {
                        family += it.deduction
                    }

                    //ประกัน
                    9, 11 -> {
                        //เช็คเงื่อนไข  9+10รวมกันไม่เกิน100000 11ไม่เกิน200000นำไปหักส่วนบนก่อน
                        /*if(insuranceMax>0){
                            insurance += it.deduction
                            insuranceMax -= it.deduction
                        }*/
                        insurance += it.deduction
                    }
                    10 ->{
//                        if(insuranceMax>0){
//                            insuranceDiff = insuranceMax
//                            insuranceMax = 0.0
//                            var cal = it.deduction-insuranceDiff
//                            if(cal>200000.0){
//                                insurance +=200000.0
//                            }else insurance+= cal
//                        }
                        insurance += it.deduction
                    }
                    8, 12, 13 -> {
                        sum += it.deduction
                    }

                    //กองทุน
                    14, 15, 16, 17, 18, 19 -> {
                        //ไม่เกินเปอเซนของเงินคำนวณ รวมกันไม่เกิน500000
//                        if(fundsMax>0){
//                            if(it.deduction > fundsMax){
//                                fund += fundsMax
//                                fundsMax = 0.0
//                            }else{
//                                fundsMax-=it.deduction
//                                fund +=it.deduction
//                            }
//                        }
                        fund += it.deduction
                    }

                    20 -> {
                        sum += it.deduction
                    }
                    21, 22 -> {
                        //ไม่เกินเปอ
                        sum += it.deduction
                    }
                    23 -> {
                        sum += it.deduction
                    }
                }
            }
            sum+family+fund+insurance
        } else {
            0.0
        }
    }

    fun calculateTaxIncome(): Double {
        var sum = 0.0
        if (incomeList.value != null && deductList.value != null) {
            if (calculateDeduct(deductList.value) > calculateIncome(incomeList.value)) {
                return 0.0
            }
//            incomeList.value!!.forEach {
//                sum += it.income
//            }
            sum += calculateIncome(incomeList.value)

//            deductList.value!!.forEach {
//                sum -= it.deduction
//            }
            sum -= calculateDeduct(deductList.value)
            return sum
        } else if (incomeList.value != null && deductList.value == null) {
            return calculateIncome(incomeList.value)
        } else {
            return 0.0
        }
    }

    fun vatValue(): String {
        val data = calculateVat()
        val data2 = BigDecimal(abs(data))
        val formatData = String.format("%,.2f", data2)
        return if (data < 0) {
            "ได้รับเงินคืนภาษี $formatData"
        } else {
            "ต้องจ่ายภาษี $formatData"
        }
    }

    suspend fun calculateDeductProfile(
        type: Int,
        age: Int,
        sick: Boolean,
        incompetent: Boolean
    ): Double = withContext(Dispatchers.IO) {
        var value = 0.0

        when (type) {
            0 -> {
                if (db.getDeductByType(type).isEmpty()) {
                    if (sick) {
                        value = TypeDeductList.data[type].deductionMax + 190000.0
                    } else {
                        value = TypeDeductList.data[type].deductionMax
                    }
                } else value = 0.0
            }
            1, 2 -> {
                if (db.getDeductByType(type).size < 2 && age > 60) {
                    if (sick) {
                        value = TypeDeductList.data[type].deductionMax + 60000.0
                    } else {
                        value = TypeDeductList.data[type].deductionMax
                    }
                } else {
                    if (sick) {
                        value = 60000.0
                    }else value = 0.0
                }
            }
            3 -> {
                if (db.getDeductByType(type).isEmpty()) {
                    if (sick) {
                        value = TypeDeductList.data[type].deductionMax + 60000.0
                    } else {
                        value = TypeDeductList.data[type].deductionMax
                    }
                } else value = 0.0
            }
            4 -> {
                if (age > 25) {
                    if (sick && incompetent) {
                        value = 60000.0 + 30000.0
                    } else if (sick) {
                        value = 60000.0
                    } else if (incompetent) {
                        value = 30000.0
                    } else value = 0.0
                } else {
                    if (age > (Calendar.getInstance(Locale.ENGLISH).get(Calendar.YEAR) - 2018)) {
                        if (sick) {
                            value = 60000.0 + 30000.0
                        } else value = 30000.0
                    } else {
                        if (db.getDeductByType(type).isEmpty()) {
                            if (sick) {
                                value = 60000.0 + 30000.0
                            } else value = 30000.0
                        } else {
                            if (sick) {
                                value = 60000.0 + 60000.0
                            } else value = 60000.0
                        }
                    }
                }
            }
            5 -> {
                if (db.getDeductByType(4).size < 3) {
                    if (db.getDeductByType(4).size + db.getDeductByType(5).size < 3) {
                        if (age > 25) {
                            if (sick && incompetent) {
                                value = 60000.0 + 30000.0
                            } else if (sick) {
                                value = 60000.0
                            } else if (incompetent) {
                                value = 30000.0
                            } else value = 0.0
                        } else {
                            if (sick) {
                                value = 30000.0 + 60000.0
                            } else value = 30000.0
                        }
                    } else value = 0.0
                } else value = 0.0
            }
            6 -> {
                if (db.getDeductByType(type).isEmpty()) {
                    value = 60000.0
                } else value = 0.0
            }
        }

        value
    }

    fun calculateDeductMax(type: Int, income:Double): Double {
        var deductPercent = 0.0
        when (type){
            11->{
                deductPercent = 0.15*income
            }
            16 ->{
                deductPercent = 0.15*income
            }
            14,15 ->{
                deductPercent = income * 0.3
            }
            21,22 ->{
                deductPercent = income * 0.1
            }
            else -> return TypeDeductList.data[type].deductionMax
        }
        if(type==21){
            return deductPercent
        }
        if(deductPercent < TypeDeductList.data[type].deductionMax){
            return deductPercent
        }else return TypeDeductList.data[type].deductionMax
    }
    suspend fun suggestDeduction() : List<DeductType> = withContext(Dispatchers.IO){
        var suggest = mutableListOf<DeductType>()
        var index = 0

        do{
            when(index){
                0,1,2,3,4,5,6,7,8 ->{
                    if(db.getDeductByType(index).isEmpty()){
                        suggest.add(TypeDeductList.data[index])
                    }
                }
                9,11->{
                    if(insuranceMax>0){
                        suggest.add(TypeDeductList.data[index])
                    }
                }
                10->{
                    var cal = 0.0
                    db.getDeductByType(index).forEach {
                        cal += it.deduction
                    }
                    if(db.getDeductByType(index).isEmpty()) {
                        suggest.add(TypeDeductList.data[index])
                    }else if(cal<200000.0) {
                        suggest.add(TypeDeductList.data[index])
                    }
                }
                12,13->{
                    var cal = 0.0
                    db.getDeductByType(index).forEach {
                        cal += it.deduction
                    }
                    if(db.getDeductByType(index).isEmpty()) {
                        suggest.add(TypeDeductList.data[index])
                    }else if(cal<15000.0) {
                        suggest.add(TypeDeductList.data[index])
                    }
                }
                14,15,16,17,18,19->{
                    var cal = 0.0
                    db.getDeductByType(index).forEach {
                        cal += it.deduction
                    }
                    if(db.getDeductByType(index).isEmpty()) {
                        suggest.add(TypeDeductList.data[index])
                    }else if(cal<500000.0) {
                        suggest.add(TypeDeductList.data[index])
                    }
                }
                20,21,22,23->{
                    if(db.getDeductByType(index).isEmpty()){
                        suggest.add(TypeDeductList.data[index])
                    }
                }
            }
        }while (index++<23)
        suggest
    }
}
