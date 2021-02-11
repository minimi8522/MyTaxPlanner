package com.example.mytaxplanner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mytaxplanner.SharedViewModel
import com.example.mytaxplanner.databinding.FragmentPersonalTaxBinding

class PersonalTaxFragment : Fragment() {
    lateinit var binding: FragmentPersonalTaxBinding
    //

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalTaxBinding.inflate(layoutInflater)
        return binding.root
    }
}