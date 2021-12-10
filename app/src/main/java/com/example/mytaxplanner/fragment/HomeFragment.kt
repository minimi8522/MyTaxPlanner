package com.example.mytaxplanner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytaxplanner.R
import com.example.mytaxplanner.databinding.FragmentHomeBinding
import com.example.mytaxplanner.viewmodel.SharedViewModel
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.createBalloon

class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var balloon1: Balloon
    private lateinit var balloon2: Balloon

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        balloon1 = createBalloon(requireContext()) {
            setArrowSize(10)
            setWidth(BalloonSizeSpec.WRAP)
            setPadding(8)
            setHeight(BalloonSizeSpec.WRAP)
            setArrowPosition(0.5f)
            setArrowSize(12)
            setCornerRadius(4f)
            setAlpha(1f)
            setLayout(R.layout.card_income_info)
            setTextColorResource(R.color.white)
            setTextIsHtml(true)
            setBackgroundColorResource(R.color.teal_100)
            setBalloonAnimation(BalloonAnimation.FADE)
            setLifecycleOwner(lifecycleOwner)
        }
        balloon1.setOnBalloonOutsideTouchListener { _, _ ->
            balloon1.dismiss()
        }

        balloon2 = createBalloon(requireContext()) {
            setArrowSize(10)
            setWidth(BalloonSizeSpec.WRAP)
            setPadding(8)
            setHeight(BalloonSizeSpec.WRAP)
            setArrowPosition(0.5f)
            setArrowSize(12)
            setCornerRadius(4f)
            setAlpha(1f)
            setLayout(R.layout.card_income_info)
            setTextColorResource(R.color.white)
            setTextIsHtml(true)
            setBackgroundColorResource(R.color.teal_100)
            setBalloonAnimation(BalloonAnimation.FADE)
            setLifecycleOwner(lifecycleOwner)
        }
        balloon2.setOnBalloonOutsideTouchListener { _, _ ->
            balloon2.dismiss()
        }

        binding.imvInfo1.setOnClickListener {
            balloon1.showAlignBottom(it)
        }

        binding.imvInfo2.setOnClickListener {
            balloon2.showAlignBottom(it)
        }


        viewModel.incomeList.observe(viewLifecycleOwner, { list ->
            binding.tvIncome.text = String.format("%,.2f", viewModel.calculateTaxIncome.value)
            binding.tvTaxPaid.text = String.format("%,.2f", viewModel.calculateTaxPaid(list))
            binding.vatValue.text = viewModel.vatValue()
            binding.tvIncomeReceive.text = String.format("%,.2f", viewModel.calculateIncome(list)) + " บาท"
        })
        viewModel.deductList.observe(viewLifecycleOwner,{ list ->
            binding.tvIncome.text = String.format("%,.2f", viewModel.calculateTaxIncome.value)
            binding.vatValue.text = viewModel.vatValue()
            binding.tvDeduct.text = String.format("%,.2f", viewModel.calculateDeduct(list)) +" บาท"
        })

//        viewModel.calculateTaxIncome.observe(viewLifecycleOwner, {
//            binding.tvIncome.text = String.format("%,.2f", it)
//        })
    }
//    TODO("แก้บัค")
}