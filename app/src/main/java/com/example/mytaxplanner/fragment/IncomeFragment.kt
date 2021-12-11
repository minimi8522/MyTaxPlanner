package com.example.mytaxplanner.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytaxplanner.R
import com.example.mytaxplanner.adapter.IncomeAdapter
import com.example.mytaxplanner.databinding.FragmentIncomeBinding
import com.example.mytaxplanner.model.DeductData
import com.example.mytaxplanner.model.IncomeData
import com.example.mytaxplanner.model.TypeDeductList
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.createBalloon

class IncomeFragment() : BaseFragment() {

    private lateinit var binding: FragmentIncomeBinding
    private val adapter = IncomeAdapter(listOf())
    private lateinit var balloon1: Balloon

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncomeBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.incomeRecycle.layoutManager = LinearLayoutManager(requireContext())
        binding.incomeRecycle.adapter = adapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(v: RecyclerView, h: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false
            override fun onSwiped(h: RecyclerView.ViewHolder, dir: Int) = viewModel.removeIncomeAt(viewModel.incomeList.value?.get(h.adapterPosition)!!.id)
        }).attachToRecyclerView(binding.incomeRecycle)

        binding.button4.setOnClickListener {
            val dialog = AddIncomeFragment()
            dialog.show(requireActivity().supportFragmentManager, "addIncome")
        }

        viewModel.incomeList.observe(viewLifecycleOwner, { list ->
            if (list.isNotEmpty()) {
                adapter.updateList(list.map { IncomeData(it.type,it.income, it.incomeVAT)})
                binding.imgEmpty.visibility = View.GONE
            } else {
                binding.imgEmpty.visibility = View.VISIBLE
            }

        })

        viewModel.calculateTaxIncome.observe(viewLifecycleOwner, {
            binding.tvIncomeResult.text = "$it บาท"
        })

        balloon1 = createBalloon(requireContext()) {
            setArrowSize(10)
            setWidth(BalloonSizeSpec.WRAP)
            setPadding(8)
            setHeight(BalloonSizeSpec.WRAP)
            setArrowPosition(0.31f)
            setArrowSize(12)
            setCornerRadius(4f)
            setAlpha(1f)
            setLayout(R.layout.card_income_info)
            setTextIsHtml(true)
            setBackgroundColorResource(R.color.teal_100)
            setBalloonAnimation(BalloonAnimation.FADE)
            setLifecycleOwner(lifecycleOwner)
        }
        balloon1.setOnBalloonOutsideTouchListener { _, _ ->
            balloon1.dismiss()
        }

    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.single_info_menu, menu)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.icon_info -> {
                balloon1.showAlignTop(binding.root,0,0)
                //Toast.makeText(requireContext(),"Income info click",Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}