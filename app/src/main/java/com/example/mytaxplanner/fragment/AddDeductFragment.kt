package com.example.mytaxplanner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.example.mytaxplanner.R
import com.example.mytaxplanner.databinding.FragmentAddDeductionBinding
import com.example.mytaxplanner.model.TypeDeductList
import com.example.mytaxplanner.util.DecimalDigitsInputFilter

class AddDeductFragment : BaseFragment() {
    private lateinit var binding: FragmentAddDeductionBinding

    private var selectType : Int = 0
    private var typeList = TypeDeductList.data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDeductionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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