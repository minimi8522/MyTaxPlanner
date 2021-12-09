package com.example.mytaxplanner.fragment

import android.R
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytaxplanner.databinding.FragmentAddIncomeBinding
import com.example.mytaxplanner.model.TypeIncomeList
import com.example.mytaxplanner.model.entity.IncomeDataEntity
import com.example.mytaxplanner.repository.TaxPlannerDatabase
import com.example.mytaxplanner.util.DecimalDigitsInputFilter
import com.example.mytaxplanner.viewmodel.SharedViewModel

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
            spType.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1, data)
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
                if (/*!selectType.isNullOrEmpty() &&*/ etIncome.text.isNotEmpty() && etDeduct.text.isNotEmpty()) {
                    viewModel.addIncomeData(selectType,etIncome.text.toString().toDouble() , etDeduct.text.toString().toDouble())
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