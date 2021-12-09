package com.example.mytaxplanner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytaxplanner.R
import com.example.mytaxplanner.adapter.DeductAdapter
import com.example.mytaxplanner.adapter.IncomeAdapter
import com.example.mytaxplanner.databinding.FragmentTaxBinding
import com.example.mytaxplanner.model.DeductData
import com.example.mytaxplanner.model.IncomeData
import com.example.mytaxplanner.model.TypeDeductList
import com.example.mytaxplanner.repository.TaxPlannerDatabase
import com.example.mytaxplanner.viewmodel.SharedViewModel

class TaxFragment : BaseFragment() {
    private lateinit var binding: FragmentTaxBinding
    private val adapter = DeductAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaxBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.deductRecycle.layoutManager = LinearLayoutManager(requireContext())
        binding.deductRecycle.adapter = adapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(v: RecyclerView, h: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false
            override fun onSwiped(h: RecyclerView.ViewHolder, dir: Int) = viewModel.removeDeductAt(viewModel.deductList.value?.get(h.adapterPosition)!!.id)
        }).attachToRecyclerView(binding.deductRecycle)

        binding.btnAdd.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment,AddDeductFragment())?.addToBackStack(null)?.commit()
        }
        binding.btnProfile.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment,PersonalTaxFragment())?.addToBackStack(null)?.commit()
        }
        viewModel.deductList.observe(viewLifecycleOwner, { list ->
            if (list.isNotEmpty()) {
                adapter.updateList(list.map { DeductData(it.deductType, it.deduction,TypeDeductList.data[it.deductType].deductionMax) })
                binding.imgEmpty.visibility = View.GONE
            } else {
                binding.imgEmpty.visibility = View.VISIBLE
            }
        })

//        val db = TaxPlannerDatabase.getInstance(requireContext()).taxPlannerDao
//        db.insertIncomeData()
    }
}