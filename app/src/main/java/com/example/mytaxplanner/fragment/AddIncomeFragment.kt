package com.example.mytaxplanner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mytaxplanner.R
import com.example.mytaxplanner.databinding.FragmentAddIncomeBinding
import com.example.mytaxplanner.model.TypeIncomeList
import com.example.mytaxplanner.util.DecimalDigitsInputFilter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AddIncomeFragment : BaseFragment() {
    private lateinit var binding: FragmentAddIncomeBinding

    private var selectType : Int = 0
    private var typeList = TypeIncomeList.data

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
            val data = mutableListOf<String>()
            typeList.forEach { data.add(it.description) }
            spType.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, data)
            etIncome.filters = arrayOf(DecimalDigitsInputFilter(null,2))
            etDeduct.filters = arrayOf(DecimalDigitsInputFilter(null,2))

            spType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    selectType = position
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
//                    TODO("Not yet implemented")
                }

            }
            btnConfirm.setOnClickListener {
                if (etIncome.text.isNotEmpty() && etDeduct.text.isNotEmpty()) {
                    if (etDeduct.text.toString().toDouble() > etIncome.text.toString().toDouble()) {
                        val dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme).apply {
                            setTitle("แจ้งเตือน")
                            setMessage("รายได้ต้องมากกว่าหัก ณ ที่จ่าย")
                            setIcon(R.drawable.ic_round_warning_24)
                            setCancelable(false)
                            setPositiveButton("เข้าใจแล้ว"){ dialogInterface, which ->
                                dialogInterface.dismiss()
                            }
                        }
                        dialog.show()
                    } else {
                        viewModel.addIncomeData(selectType,etIncome.text.toString().toDouble() , etDeduct.text.toString().toDouble())
                        Toast.makeText(requireContext(),"เพิ่มรายการรายได้เรียบร้อย!!", Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.popBackStack()
                    }


                } else {
                    val builder = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme).apply {
                        setTitle("แจ้งเตือน")
                        setMessage("กรุณาระบุให้ครบ")
                        setIcon(R.drawable.ic_round_warning_24)
                        setCancelable(false)
                        setPositiveButton("เข้าใจแล้ว"){ dialogInterface, which ->
                            dialogInterface.dismiss()
                        }
                    }
                    builder.show()
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