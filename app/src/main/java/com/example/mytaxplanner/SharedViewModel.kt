package com.example.mytaxplanner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel() : ViewModel() {

    private var mTaxDeduction : MutableLiveData<ArrayList<TaxDeductionData>>? = null

    init {
        val mockData = ArrayList<TaxDeductionData>()
        mockData.add(TaxDeductionData(0,"ประเภทลดหย่อนครอบครัว",0.0, 0.0,1))
        mockData.add(TaxDeductionData(1,"ลดหย่อนส่วนบุคคล",0.0, 60000.0,2))
        mockData.add(TaxDeductionData(2,"ลดหย่อนคู่สมรส",0.0, 60000.0,2))
        mockData.add(TaxDeductionData(3,"ลดหย่อนบิดา",0.0, 30000.0,2))
        mockData.add(TaxDeductionData(4,"ลดหย่อนมารดา",0.0, 30000.0,2))
        mockData.add(TaxDeductionData(5,"ประเภทลดหย่อนทั่วไป",0.0, 0.0,1))
        mTaxDeduction?.postValue(mockData)
    }

    fun getAllTax() : MutableLiveData<ArrayList<TaxDeductionData>>? {
        return mTaxDeduction
    }
}