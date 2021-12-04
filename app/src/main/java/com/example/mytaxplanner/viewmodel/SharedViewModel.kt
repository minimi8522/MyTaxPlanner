package com.example.mytaxplanner.viewmodel

import android.app.Application
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import com.example.mytaxplanner.model.DeductData
import com.example.mytaxplanner.model.IncomeData
import com.example.mytaxplanner.model.TaxData
import com.example.mytaxplanner.model.entity.DeductDataEntity
import com.example.mytaxplanner.model.entity.IncomeDataEntity
import com.example.mytaxplanner.repository.TaxPlannerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel(val context: Application) : AndroidViewModel(context) {

//    private var _incomeList = MutableLiveData<MutableList<IncomeData>>()
//    val incomeList : LiveData<MutableList<IncomeData>> = _incomeList


//    val deductList : LiveData<MutableList<DeductData>> = _deductList

    val db = TaxPlannerDatabase.getInstance(context).taxPlannerDao
    var incomeList : LiveData<List<IncomeDataEntity>>

    var deductList : LiveData<List<DeductDataEntity>>

    init {
        incomeList = db.getIncome()
        deductList = db.getDeduct()

//        _incomeList.value = db.getIncome().
//        _deductList.value = mutableListOf()
    }

    fun addIncomeData(income: Double , incomeVAT:Double) {
       // _incomeList.value?.add(IncomeData(income,incomeVAT))
        viewModelScope.launch(Dispatchers.IO) {

            db.insertIncomeData(IncomeDataEntity(0, income, incomeVAT))
        }
    }
    fun addDeductData(deductType: Int , deductVal:Double) {
//        _deductList.value?.add(DeductData(deductType,deductVal))

        val data = DeductData(deductType,deductVal)

        viewModelScope.launch(Dispatchers.IO) {

            db.insertDeductData(DeductDataEntity(0, data.deductionType , data.deduction, data.deductionMax))
        }
    }

    fun removeAt(index: Int) {
//        if (!_incomeList.value.isNullOrEmpty()) {
//            val oldValue = _incomeList.value
//            oldValue?.removeAt(index).also { _incomeList.value = oldValue!! }
//        } else {
//            _incomeList.value = mutableListOf()
//        }
    }

    fun calculateIncome(list: List<IncomeDataEntity>?) : String {
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