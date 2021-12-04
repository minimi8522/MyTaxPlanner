package com.example.mytaxplanner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytaxplanner.R
import com.example.mytaxplanner.adapter.DeductAdapter
import com.example.mytaxplanner.adapter.IncomeAdapter
import com.example.mytaxplanner.databinding.FragmentTaxBinding
import com.example.mytaxplanner.model.DeductData
import com.example.mytaxplanner.model.IncomeData
import com.example.mytaxplanner.repository.TaxPlannerDatabase
import com.example.mytaxplanner.viewmodel.SharedViewModel

class TaxFragment : BaseFragment() {
    private lateinit var binding: FragmentTaxBinding
    private val adapter = DeductAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaxBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.deductRecycle.layoutManager = LinearLayoutManager(requireContext())
        binding.deductRecycle.adapter = adapter

        binding.btnAdd.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment,AddDeductFragment())?.addToBackStack(null)?.commit()
        }
        binding.btnProfile.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment,PersonalTaxFragment())?.addToBackStack(null)?.commit()
        }
        viewModel.deductList.observe(viewLifecycleOwner, { list ->
            adapter.updateList(list.map { DeductData(it.deductType, it.deduction) })
        })

//        val db = TaxPlannerDatabase.getInstance(requireContext()).taxPlannerDao
//        db.insertIncomeData()
    }
}