package com.example.mytaxplanner.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytaxplanner.BuildConfig
import com.example.mytaxplanner.databinding.CardIncomeBinding
import com.example.mytaxplanner.model.IncomeData
import com.example.mytaxplanner.model.TypeIncomeList

class IncomeAdapter(var incomeDataList: List<IncomeData>) : RecyclerView.Adapter<IncomeAdapter.ViewHolder>() {
    private var typeList = TypeIncomeList.data

    inner class ViewHolder(view : CardIncomeBinding) : RecyclerView.ViewHolder(view.root) {
        val getIncome = view.tvIncome
        val getTaxPaid = view.tvTaxPaid
        val getTaxType = view.tvIncomeType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardIncomeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.getIncome.text = "${incomeDataList[position].income}  บาท"
        holder.getTaxPaid.text = "${incomeDataList[position].incomeVAT}  บาท"
        holder.getTaxType.text = typeList[incomeDataList[position].type].description
    }

    override fun getItemCount(): Int {
        return incomeDataList.size
    }

    fun updateList(incomeList : List<IncomeData>) {
        incomeDataList = incomeList
        notifyDataSetChanged()
    }
}
