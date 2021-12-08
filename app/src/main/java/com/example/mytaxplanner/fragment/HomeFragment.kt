package com.example.mytaxplanner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytaxplanner.databinding.FragmentHomeBinding
import com.example.mytaxplanner.viewmodel.SharedViewModel

class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.incomeList.observe(viewLifecycleOwner, { list ->
            binding.tvIncome.text = String.format("%,.2f", viewModel.calculateTaxIncome())
            binding.tvTaxPaid.text = String.format("%,.2f", viewModel.calculateTaxPaid(list))
            binding.vatValue.text = viewModel.vatValue()
        })
        viewModel.deductList.observe(viewLifecycleOwner,{
            binding.tvIncome.text = String.format("%,.2f", viewModel.calculateTaxIncome())
            binding.vatValue.text = viewModel.vatValue()
        })
    }
//    TODO("แก้บัค")
}