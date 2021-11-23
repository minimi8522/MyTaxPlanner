package com.example.mytaxplanner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytaxplanner.R
import com.example.mytaxplanner.databinding.CardIncomeBinding
import com.example.mytaxplanner.model.IncomeData

class IncomeAdapter(var incomeDataList: List<IncomeData>) : RecyclerView.Adapter<IncomeAdapter.ViewHolder>() {
    inner class ViewHolder(view : CardIncomeBinding) : RecyclerView.ViewHolder(view.root) {
        val getIncome = view.tvIncome
        val getDeduct = view.tvDeduct
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardIncomeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.getIncome.text = incomeDataList[position].income.toString()
        holder.getDeduct.text = incomeDataList[position].incomeVAT.toString()
    }

    override fun getItemCount(): Int {
        return incomeDataList.size
    }

    fun updateList(incomeList : List<IncomeData>) {
        incomeDataList = incomeList
        notifyDataSetChanged()
    }
}
