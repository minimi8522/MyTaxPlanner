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

class SharedViewModel(val context: Application) : AndroidViewModel(context) {

    companion object {
        const val LV0 = 0
        const val LV1 = 5
        const val LV2 = 10
        const val LV3 = 15
        const val LV4 = 20
        const val LV5 = 25
        const val LV6 = 30
        const val LV7 = 35
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

    fun addIncomeData(income: Double, incomeVAT: Double) {
        // _incomeList.value?.add(IncomeData(income,incomeVAT))
        viewModelScope.launch(Dispatchers.IO) {

            db.insertIncomeData(IncomeDataEntity(0, income, incomeVAT))
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

    fun calculate(): Double {
        val income = 100000
        val deduct = 10000
        var vat = 0.0

        var incomeCal = income - deduct
        when (incomeCal) {
            in 0..150000 -> {

            }
            in 150001..300000 -> {
                vat += (incomeCal - 150000) * LV1
                vat += 0
            }
            in 300001..500000 -> {
                vat += (incomeCal - 300000) * LV2
                vat += 7500
            }
            in 500001..750000 -> {
                vat += (incomeCal - 500000) * LV3
                vat += 27500
            }
            in 750001..1000000 -> {
                vat += (incomeCal - 750000) * LV4
                vat += 65000
            }
            in 1000001..2000000 -> {
                vat += (incomeCal - 1000000) * LV5
                vat += 115000
            }
            in 2000001..5000000 -> {
                vat += (incomeCal - 2000000) * LV6
                vat += 365000
            }
            else -> {
                vat += (incomeCal - 5000000) * LV7
                vat += 12650000
            }
        }
        return vat
    }


    fun calculateIncome(list: List<IncomeDataEntity>?): String {
        return if (list != null) {
            var sum = 0.0
            list?.forEach {
                sum += it.income
            }
            sum.toString()
        } else {
            "0.0"
        }
    }
}