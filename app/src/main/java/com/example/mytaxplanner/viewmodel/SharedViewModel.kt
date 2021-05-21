package com.example.mytaxplanner.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytaxplanner.model.TaxData

class SharedViewModel : ViewModel() {

    private var mutableTaxData : MutableLiveData<TaxData> = MutableLiveData()
    private var data : TaxData = TaxData()

    init {
        mutableTaxData.postValue(data)
    }

    fun getTaxData() : MutableLiveData<TaxData> {
        return mutableTaxData
    }
}