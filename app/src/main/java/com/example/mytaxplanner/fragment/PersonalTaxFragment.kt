package com.example.mytaxplanner.fragment

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.example.mytaxplanner.R
import com.example.mytaxplanner.databinding.FragmentPersonalTaxBinding
import com.example.mytaxplanner.model.TypeDeductList
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class PersonalTaxFragment : BaseFragment() {
    private lateinit var binding: FragmentPersonalTaxBinding

    private var selectType: Int = 0
    private var typeList = TypeDeductList.data

    private var deductVal : Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalTaxBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val data = mutableListOf<String>()
            typeList.forEach { if (it.type <= 7) data.add(it.description) }
            spType.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, data)
            spType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    selectType = position
                    tvMaxDeductVal.text = typeList[selectType].deductionMax.toString()
                    deductVal = tvMaxDeductVal.text.toString().toDouble()
                    if(position == 4){
                        binding.textIncompetent.visibility = View.VISIBLE
                        binding.rgIncompetent.visibility = View.VISIBLE
                    } else{
                        binding.textIncompetent.visibility = View.GONE
                        binding.rgIncompetent.visibility = View.GONE
                    }
                    if(position == 6){
                        binding.textSickness.visibility = View.GONE
                        binding.rgSick.visibility = View.GONE
                    }else{
                        binding.textSickness.visibility = View.VISIBLE
                        binding.rgSick.visibility = View.VISIBLE
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
//                    TODO("Not yet implemented")
                }

            }
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .build()

            datePicker.addOnPositiveButtonClickListener {
                etAge.setText(getDate(it))
            }
            ivCalendar.setOnClickListener {
                datePicker.show(requireActivity().supportFragmentManager, "calendar")
            }

//            binding.rgSick.setOnCheckedChangeListener(rgSick,rbNo){
//                if(binding.rbYes.isChecked){
//                    tvMaxDeductVal.text = typeList[selectType].deductionMax.toString()+60000.0
//                    deductVal += 60000.0
//                }
//                else{
//                    deductVal -=60000.0
//                    tvMaxDeductVal.text = deductVal.toString()
//                }
//            }

            binding.button.setOnClickListener {
            if (binding.rgSick.checkedRadioButtonId == -1 || etAge.text.isEmpty()){
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("แจ้งเตือน")
                builder.setMessage("กรุณาระบุให้ครบ")
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                builder.setCancelable(false)
                builder.setPositiveButton("Yes"){dialogInterface, which ->
                    dialogInterface.dismiss()
                }.show()
            } else {
                viewModel.addDeductData(selectType , tvMaxDeductVal.text.toString().toDouble())
                requireActivity().supportFragmentManager.popBackStack()
            }

            }

        }
    }

    fun getDate(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp
        val date = DateFormat.format("yyyy-MM-dd", calendar).toString()
        return date
    }
}

