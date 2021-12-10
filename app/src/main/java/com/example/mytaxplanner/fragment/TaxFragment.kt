package com.example.mytaxplanner.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytaxplanner.R
import com.example.mytaxplanner.adapter.DeductAdapter
import com.example.mytaxplanner.databinding.FragmentTaxBinding
import com.example.mytaxplanner.model.DeductData
import com.example.mytaxplanner.model.TypeDeductList

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
            val dialog = AddDeductFragment()
            dialog.show(requireActivity().supportFragmentManager, "addDeduct")
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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.single_info_menu, menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.icon_info -> {
                Toast.makeText(requireContext(),"Tax info click", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}