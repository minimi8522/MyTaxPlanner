package com.example.mytaxplanner.fragment

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytaxplanner.adapter.IncomeAdapter
import com.example.mytaxplanner.adapter.SuggestAdapter
import com.example.mytaxplanner.databinding.FragmentSuggestBinding
import com.example.mytaxplanner.viewmodel.SharedViewModel
import kotlinx.coroutines.launch

class SuggestFragment : BaseFragment() {
    private lateinit var binding: FragmentSuggestBinding
//    private val adapter = SuggestAdapter(viewModel.suggestDeduction())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuggestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            binding.rvSuggest.adapter = SuggestAdapter(viewModel.suggestDeduction())
            binding.rvSuggest.layoutManager = LinearLayoutManager(requireContext())
            if(viewModel.suggestDeduction().isEmpty()){
                binding.textView2.visibility = View.VISIBLE
            }else {
                binding.textView2.visibility = View.GONE
            }
        }

    }
}