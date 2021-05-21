package com.example.mytaxplanner.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytaxplanner.databinding.FragmentFileBinding
import com.example.mytaxplanner.viewmodel.SharedViewModel

class FileFragment : BaseFragment() {
    private lateinit var binding : FragmentFileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =  activity?.run {
            ViewModelProvider(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        binding.button5.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.type = "file/*"
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            val chooserIntent = Intent.createChooser(galleryIntent, "Select Source")
            startActivityForResult(chooserIntent, 1000)
        }

        viewModel.getTaxData().observe(viewLifecycleOwner, Observer {

        })
    }
}