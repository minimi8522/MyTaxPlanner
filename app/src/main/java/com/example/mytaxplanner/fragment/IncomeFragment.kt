package com.example.mytaxplanner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytaxplanner.R
import com.example.mytaxplanner.adapter.IncomeAdapter
import com.example.mytaxplanner.databinding.FragmentIncomeBinding
import com.example.mytaxplanner.model.IncomeData

class IncomeFragment() : BaseFragment() {

    private lateinit var binding: FragmentIncomeBinding
    private val adapter = IncomeAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.incomeRecycle.layoutManager = LinearLayoutManager(requireContext())
        binding.incomeRecycle.adapter = adapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(v: RecyclerView, h: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false
            override fun onSwiped(h: RecyclerView.ViewHolder, dir: Int) = viewModel.removeAt(h.adapterPosition)
        }).attachToRecyclerView(binding.incomeRecycle)

        binding.button4.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment,AddIncomeFragment())?.addToBackStack(null)?.commit()
        }

        viewModel.incomeList.observe(viewLifecycleOwner, { list ->
            adapter.updateList(list)
        })


    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IncomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IncomeFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}