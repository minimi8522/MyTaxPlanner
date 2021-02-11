package com.example.mytaxplanner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytaxplanner.R
import com.example.mytaxplanner.TaxDeductionAdapter
import com.example.mytaxplanner.TaxDeductionData
import com.example.mytaxplanner.databinding.FragmentTaxBinding

class TaxFragment : Fragment() {
    lateinit var binding: FragmentTaxBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mockData = ArrayList<TaxDeductionData>()
        mockData.add(TaxDeductionData(0,"ประเภทลดหย่อนครอบครัว",0.0, 0.0,1))
        mockData.add(TaxDeductionData(1,"ลดหย่อนส่วนบุคคล",0.0, 60000.0,2))
        mockData.add(TaxDeductionData(2,"ลดหย่อนคู่สมรส",0.0, 60000.0,2))
        mockData.add(TaxDeductionData(3,"ลดหย่อนบิดา",0.0, 30000.0,2))
        mockData.add(TaxDeductionData(4,"ลดหย่อนมารดา",0.0, 30000.0,2))
        mockData.add(TaxDeductionData(5,"ประเภทลดหย่อนทั่วไป",0.0, 0.0,1))

        binding.recycleView.layoutManager = LinearLayoutManager(context)
        binding.recycleView.adapter = TaxDeductionAdapter(mockData)
        binding.button2.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment,PersonalTaxFragment())?.addToBackStack(null)?.commit()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaxBinding.inflate(layoutInflater)
        return binding.root
    }

}