package com.example.mytaxplanner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytaxplanner.R
import com.example.mytaxplanner.databinding.FragmentTaxBinding
import com.example.mytaxplanner.viewmodel.SharedViewModel

class TaxFragment : BaseFragment() {
    private lateinit var binding: FragmentTaxBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaxBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =  activity?.run {
            ViewModelProvider(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        binding.btnAdd.setOnClickListener {
//            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment,AddIncomeFragment())?.addToBackStack(null)?.commit()
        }
        binding.btnProfile.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment,PersonalTaxFragment())?.addToBackStack(null)?.commit()
        }

    }
}