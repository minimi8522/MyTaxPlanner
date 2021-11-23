package com.example.mytaxplanner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytaxplanner.databinding.FragmentPersonalTaxBinding
import com.example.mytaxplanner.viewmodel.SharedViewModel

class PersonalTaxFragment : BaseFragment() {
    private lateinit var binding: FragmentPersonalTaxBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalTaxBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            if (binding.radioGroup.checkedRadioButtonId == -1 || binding.radioGroup1.checkedRadioButtonId == -1 ||binding.radioGroup2.checkedRadioButtonId == -1 ){
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("แจ้งเตือน")
                builder.setMessage("กรุณาระบุให้ครบ")
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                builder.setCancelable(false)
                builder.setPositiveButton("Yes"){dialogInterface, which ->
                    dialogInterface.dismiss()
                }.show()
            } else {
//                viewModel.
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

    }
}