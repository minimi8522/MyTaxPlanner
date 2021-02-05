package com.example.mytaxplanner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytaxplanner.TaxDeductionAdapter
import com.example.mytaxplanner.TaxDeductionData
import com.example.mytaxplanner.databinding.FragmentTaxBinding

class TaxFragment : Fragment() {
    lateinit var binding: FragmentTaxBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mockData = ArrayList<TaxDeductionData>()
        mockData.add(TaxDeductionData("ประเภทลดหย่อนครอบครัว",0.0, 0.0,1))
        mockData.add(TaxDeductionData("ลดหย่อนส่วนบุคคล",0.0, 60000.0,2))
        mockData.add(TaxDeductionData("ลดหย่อนคู่สมรส",0.0, 60000.0,2))
        mockData.add(TaxDeductionData("ลดหย่อนบิดา",0.0, 30000.0,2))
        mockData.add(TaxDeductionData("ลดหย่อนมารดา",0.0, 30000.0,2))
        mockData.add(TaxDeductionData("ประเภทลดหย่อนครอบครัว",0.0, 0.0,1))
        mockData.add(TaxDeductionData("ลดหย่อนส่วนบุคคล",0.0, 60000.0,2))
        mockData.add(TaxDeductionData("ลดหย่อนคู่สมรส",0.0, 60000.0,2))
        mockData.add(TaxDeductionData("ลดหย่อนบิดา",0.0, 30000.0,2))
        mockData.add(TaxDeductionData("ลดหย่อนมารดา",0.0, 30000.0,2))
        mockData.add(TaxDeductionData("ประเภทลดหย่อนครอบครัว",0.0, 0.0,1))
        mockData.add(TaxDeductionData("ลดหย่อนส่วนบุคคล",0.0, 60000.0,2))
        mockData.add(TaxDeductionData("ลดหย่อนคู่สมรส",0.0, 60000.0,2))
        mockData.add(TaxDeductionData("ลดหย่อนบิดา",0.0, 30000.0,2))
        mockData.add(TaxDeductionData("ลดหย่อนมารดา",0.0, 30000.0,2))
        mockData.add(TaxDeductionData("ประเภทลดหย่อนครอบครัว",0.0, 0.0,1))
        mockData.add(TaxDeductionData("ลดหย่อนส่วนบุคคล",0.0, 60000.0,2))
        mockData.add(TaxDeductionData("ลดหย่อนคู่สมรส",0.0, 60000.0,2))
        mockData.add(TaxDeductionData("ลดหย่อนบิดา",0.0, 30000.0,2))
        mockData.add(TaxDeductionData("ลดหย่อนมารดา",0.0, 30000.0,2))

        binding.recycleView.layoutManager = LinearLayoutManager(context)
        binding.recycleView.adapter = TaxDeductionAdapter(mockData)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaxBinding.inflate(layoutInflater)
        return binding.root
    }

}