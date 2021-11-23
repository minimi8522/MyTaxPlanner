package com.example.mytaxplanner.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytaxplanner.databinding.FragmentAddIncomeBinding
import com.example.mytaxplanner.viewmodel.SharedViewModel

class AddIncomeFragment : BaseFragment() {
    private lateinit var binding: FragmentAddIncomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddIncomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            etType.inputType = InputType.TYPE_CLASS_NUMBER
            etDeduct.inputType = InputType.TYPE_CLASS_NUMBER
            etIncome.inputType = InputType.TYPE_CLASS_NUMBER

            btnConfirm.setOnClickListener {
                if (binding.etType.text.isNotEmpty() && binding.etIncome.text.isNotEmpty() && binding.etDeduct.text.isNotEmpty()) {
                    viewModel.addIncomeData(binding.etIncome.text.toString().toDouble() , binding.etDeduct.text.toString().toDouble())
                    requireActivity().supportFragmentManager.popBackStack()
                } else {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("แจ้งเตือน")
                    builder.setMessage("กรุณาระบุให้ครบ")
                    builder.setIcon(android.R.drawable.ic_dialog_alert)
                    builder.setCancelable(false)
                    builder.setPositiveButton("Yes"){dialogInterface, which ->
                        dialogInterface.dismiss()
                    }.show()
                }
            }
        }
    }

//    companion object {
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            BlankFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}