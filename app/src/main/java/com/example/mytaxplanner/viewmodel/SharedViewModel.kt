package com.example.mytaxplanner.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mytaxplanner.model.DeductData
import com.example.mytaxplanner.model.entity.DeductDataEntity
import com.example.mytaxplanner.model.entity.IncomeDataEntity
import com.example.mytaxplanner.repository.TaxPlannerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
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

    init {
        incomeList = db.getIncome()
        deductList = db.getDeduct()

//        _incomeList.value = db.getIncome().
//        _deductList.value = mutableListOf()
    }

    fun addIncomeData(type:Int,income: Double, incomeVAT: Double) {
        // _incomeList.value?.add(IncomeData(income,incomeVAT))
        viewModelScope.launch(Dispatchers.IO) {

            db.insertIncomeData(IncomeDataEntity(0,type, income, incomeVAT))
        }
    }

    fun addDeductData(deductType: Int, deductVal: Double) {
//        _deductList.value?.add(DeductData(deductType,deductVal))

        val data = DeductData(deductType, deductVal)

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
        var vat = 0.0

        if(income == 0.0 && deduct > 0.0 ){
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
        return vat-taxPaid
    }


    fun calculateIncome(list: List<IncomeDataEntity>?): Double {
        return if (list != null) {
            var sum = 0.0
            list.forEach {
                sum += it.income
            }
            sum
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
            list.forEach {
                sum += it.deduction
            }
            sum
        } else {
            0.0
        }
    }

    fun calculateTaxIncome(): Double {
        var sum = 0.0
        if (incomeList.value != null && deductList.value != null) {
            if(calculateDeduct(deductList.value)>calculateIncome(incomeList.value)){
                return 0.0
            }
            incomeList.value!!.forEach {
                sum += it.income
            }
            deductList.value!!.forEach {
                sum -= it.deduction
            }
            return sum
        } else if (incomeList.value != null && deductList.value == null) {
            return calculateIncome(incomeList.value)
        } else {
            return 0.0
        }
    }

    fun vatValue() : String{
        val data = calculateVat()
        val data2 = BigDecimal(abs(data))
        val formatData = String.format("%,.2f", data2)
        return if(data < 0){
            "ได้รับเงินคืนภาษี $formatData"
        }else{
            "ต้องจ่ายภาษี $formatData"
        }
    }
}