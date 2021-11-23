package com.example.mytaxplanner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytaxplanner.model.IncomeData
import com.example.mytaxplanner.model.TaxData

class SharedViewModel : ViewModel() {

    private var _incomeList = MutableLiveData<MutableList<IncomeData>>()
    val incomeList : LiveData<MutableList<IncomeData>> = _incomeList

    init {
        _incomeList.value = mutableListOf()
    }

    fun addIncomeData(income: Double , incomeVAT:Double) {
        _incomeList.value?.add(IncomeData(income,incomeVAT))
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