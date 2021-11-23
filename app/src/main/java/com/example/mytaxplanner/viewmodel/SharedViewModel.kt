package com.example.mytaxplanner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytaxplanner.model.DeductData
import com.example.mytaxplanner.model.IncomeData
import com.example.mytaxplanner.model.TaxData

class SharedViewModel : ViewModel() {

    private var _incomeList = MutableLiveData<MutableList<IncomeData>>()
    val incomeList : LiveData<MutableList<IncomeData>> = _incomeList

    private var _deductList = MutableLiveData<MutableList<DeductData>>()
    val deductList : LiveData<MutableList<DeductData>> = _deductList

    init {
        _incomeList.value = mutableListOf()
        _deductList.value = mutableListOf()
    }

    fun addIncomeData(income: Double , incomeVAT:Double) {
        _incomeList.value?.add(IncomeData(income,incomeVAT))
    }
    fun addDeductData(deduct: Double , deductMax:Double) {
        _incomeList.value?.add(IncomeData(deduct,deductMax))
    }

    fun removeAt(index: Int) {
        if (!_incomeList.value.isNullOrEmpty()) {
            val oldValue = _incomeList.value
            oldValue?.removeAt(index).also { _incomeList.value = oldValue!! }
        } else {
            _incomeList.value = mutableListOf()
        }
    }
}