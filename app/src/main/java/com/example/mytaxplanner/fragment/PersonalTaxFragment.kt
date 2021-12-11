package com.example.mytaxplanner.fragment

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.coroutineScope
import com.example.mytaxplanner.R
import com.example.mytaxplanner.databinding.FragmentPersonalTaxBinding
import com.example.mytaxplanner.model.TypeDeductList
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import java.util.*

class PersonalTaxFragment : BaseFragment() {
    private lateinit var binding: FragmentPersonalTaxBinding

    private var selectType: Int = 0
    private var typeList = TypeDeductList.data

    private var age: Int = 0
    private var sick: Boolean = false
    private var incompetent: Boolean = false
    private var deductVal: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPersonalTaxBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val data = mutableListOf<String>()
            typeList.forEach { if (it.type <= 6) data.add(it.description) }
            spType.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, data)
            spType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    selectType = position
//                    tvMaxDeductVal.text = typeList[selectType].deductionMax.toString()
                    //deductVal = tvMaxDeductVal.text.toString().toDouble()

                    etAge.text.clear()
                    rgIncompetent.clearCheck()
                    rgSick.clearCheck()

                    rgIncompetent.check(R.id.rbIncompNo)
                    rgSick.check(R.id.rbSickNo)
                    age = 0
                    sick = false
                    incompetent = false

                    when (position) {
                        6 -> {
                            binding.textSickness.visibility = View.GONE
                            binding.rgSick.visibility = View.GONE
                            binding.textAge.visibility = View.GONE
                            binding.ivCalendar.visibility = View.GONE
                            binding.etAge.visibility = View.GONE
                            binding.textIncompetent.visibility = View.GONE
                            binding.rgIncompetent.visibility = View.GONE
                            etAge.setText("Default")
                        }
                        1, 2 -> {
                            binding.textSickness.visibility = View.VISIBLE
                            binding.rgSick.visibility = View.VISIBLE
                            binding.textAge.visibility = View.VISIBLE
                            binding.ivCalendar.visibility = View.VISIBLE
                            binding.etAge.visibility = View.VISIBLE
                            binding.textIncompetent.visibility = View.GONE
                            binding.rgIncompetent.visibility = View.GONE
                        }
                        0, 3, 5 -> {
                            binding.textSickness.visibility = View.VISIBLE
                            binding.rgSick.visibility = View.VISIBLE
                            binding.textAge.visibility = View.GONE
                            binding.ivCalendar.visibility = View.GONE
                            binding.etAge.visibility = View.GONE
                            binding.textIncompetent.visibility = View.GONE
                            binding.rgIncompetent.visibility = View.GONE
                            etAge.setText("Default")
                        }
                        4 -> {
                            binding.textSickness.visibility = View.VISIBLE
                            binding.rgSick.visibility = View.VISIBLE
                            binding.textAge.visibility = View.VISIBLE
                            binding.ivCalendar.visibility = View.VISIBLE
                            binding.etAge.visibility = View.VISIBLE
                            binding.textIncompetent.visibility = View.VISIBLE
                            binding.rgIncompetent.visibility = View.VISIBLE
                        }
                    }
                    viewLifecycleOwner.lifecycle.coroutineScope.launch {
                        binding.tvMaxDeductVal.text =
                            viewModel.calculateDeductProfile(selectType, age, sick, incompetent)
                                .toString()
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
                age = getAge(it)
                viewLifecycleOwner.lifecycle.coroutineScope.launch {
                    binding.tvMaxDeductVal.text =
                        viewModel.calculateDeductProfile(selectType, age, sick, incompetent)
                            .toString()
                }
                etAge.setText(getDate(it))
            }
            ivCalendar.setOnClickListener {
                if (!datePicker.isAdded) {
                    datePicker.show(requireActivity().supportFragmentManager, "calendar")
                }
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

            binding.etAge.addTextChangedListener {
                Toast.makeText(requireContext(), "calendar", Toast.LENGTH_SHORT).show()

            }
        }
        binding.rgSick.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                if (p1 == -1) {
                    // No item selected
                    sick = false
                } else {
                    if (p1 == R.id.rbSickYes) {
                        Toast.makeText(requireContext(), "yes", Toast.LENGTH_SHORT).show()
                        sick = true
                        viewLifecycleOwner.lifecycle.coroutineScope.launch {
                            binding.tvMaxDeductVal.text =
                                viewModel.calculateDeductProfile(selectType, age, sick, incompetent)
                                    .toString()
                        }
//                            deductVal = typeList[selectType].deductionMax + 60000.0
//                            tvMaxDeductVal.text = deductVal.toString()

                    } else {
                        Toast.makeText(requireContext(), "no", Toast.LENGTH_SHORT).show()
                        sick = false
                        viewLifecycleOwner.lifecycle.coroutineScope.launch {
                            binding.tvMaxDeductVal.text =
                                viewModel.calculateDeductProfile(selectType, age, sick, incompetent)
                                    .toString()
                        }
//                            deductVal = typeList[selectType].deductionMax
//                            tvMaxDeductVal.text = deductVal.toString()
                    }
                }
            }
        })

        binding.rgIncompetent.setOnCheckedChangeListener(object :
            RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                if (p1 == -1) {
                    // No item selected
                    incompetent = false

                } else {
                    if (p1 == R.id.rbIncompYes) {
                        Toast.makeText(requireContext(), "InYes", Toast.LENGTH_SHORT).show()
                        incompetent = true
                        viewLifecycleOwner.lifecycle.coroutineScope.launch {
                            binding.tvMaxDeductVal.text =
                                viewModel.calculateDeductProfile(selectType, age, sick, incompetent)
                                    .toString()
                        }
//                            deductVal = typeList[selectType].deductionMax + 60000.0
//                            tvMaxDeductVal.text = deductVal.toString()

                    } else {
                        Toast.makeText(requireContext(), "InNo", Toast.LENGTH_SHORT).show()
                        incompetent = false
                        viewLifecycleOwner.lifecycle.coroutineScope.launch {
                            binding.tvMaxDeductVal.text =
                                viewModel.calculateDeductProfile(selectType, age, sick, incompetent)
                                    .toString()
                        }
//                            deductVal = typeList[selectType].deductionMax
//                            tvMaxDeductVal.text = deductVal.toString()
                    }
                }
            }
        })
        binding.button.setOnClickListener {
            if (binding.rgSick.checkedRadioButtonId == -1 || binding.etAge.text.isEmpty()) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("แจ้งเตือน")
                builder.setMessage("กรุณาระบุให้ครบ")
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                builder.setCancelable(false)
                builder.setPositiveButton("Yes") { dialogInterface, which ->
                    dialogInterface.dismiss()
                }.show()
            } else {
                viewModel.addDeductData(selectType,
                    binding.tvMaxDeductVal.text.toString().toDouble(),
                    binding.tvMaxDeductVal.text.toString().toDouble())
                requireActivity().supportFragmentManager.popBackStack()
            }

        }

    }


    fun getDate(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp
        val date = DateFormat.format("yyyy-MM-dd", calendar).toString()
        return date
    }

    fun getAge(timestamp: Long): Int {
        val dob = Calendar.getInstance(Locale.ENGLISH)
        val now = Calendar.getInstance(Locale.ENGLISH)

        dob.timeInMillis = timestamp

        var age: Int = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        return age
    }


}

