package com.example.mytaxplanner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.mytaxplanner.R
import com.example.mytaxplanner.databinding.FragmentAddDeductionBinding
import com.example.mytaxplanner.model.TypeDeductList
import com.example.mytaxplanner.util.DecimalDigitsInputFilter
import com.example.mytaxplanner.viewmodel.SharedViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AddDeductFragment : DialogFragment() {
    private lateinit var binding: FragmentAddDeductionBinding
    protected lateinit var viewModel: SharedViewModel

    private var selectType : Int = 0
    private var typeList = TypeDeductList.data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDeductionBinding.inflate(layoutInflater)
        dialog?.setCanceledOnTouchOutside(true)
        return binding.root
    }
    override fun onStart() {
        super.onStart()

        requireDialog().window?.apply {
            this.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =  activity?.run {
            ViewModelProvider(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        binding.apply {
            val data = mutableListOf<String>()
            typeList.forEach {if(it.type>7) data.add(it.description) }
            spType.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, data)
            etDeduct.filters = arrayOf(DecimalDigitsInputFilter(null,2))
            spType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    selectType = position
                    tvMaxDeductVal.text = typeList[selectType+8].deductionMax.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
//                    TODO("Not yet implemented")
                }

            }


            btnConfirm.setOnClickListener {
                if (etDeduct.text.isNotEmpty()) {
                    viewModel.addDeductData(selectType+8 , etDeduct.text.toString().toDouble())
                    Toast.makeText(requireContext(),"เพิ่มรายการลดหย่อนเรียบร้อย!!", Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
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