package com.example.mytaxplanner.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytaxplanner.MainActivity
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

        binding.btnChooseFile.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.type = "image/*"
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            val chooserIntent = Intent.createChooser(galleryIntent, "Select Source")
            startActivityForResult(chooserIntent, 1000)
        }

        binding.btnConfirm.setOnClickListener {

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.PICK_IMAGE) {
            val selectedImage: Uri? = data?.data
            if (null != selectedImage) {
                // update the preview image in the layout
                binding.ivFile.setImageURI(selectedImage)
                binding.btnConfirm.visibility = View.VISIBLE
            }
        }
    }
}