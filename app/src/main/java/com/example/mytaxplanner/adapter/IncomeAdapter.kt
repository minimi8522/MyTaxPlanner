package com.example.mytaxplanner.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytaxplanner.BuildConfig
import com.example.mytaxplanner.MainActivity
import com.example.mytaxplanner.R
import com.example.mytaxplanner.databinding.CardIncomeBinding
import com.example.mytaxplanner.fragment.IncomeFragment
import com.example.mytaxplanner.fragment.InfoFragment
import com.example.mytaxplanner.model.IncomeData
import com.example.mytaxplanner.model.TypeIncomeList

class IncomeAdapter(var incomeDataList: List<IncomeData>) : RecyclerView.Adapter<IncomeAdapter.ViewHolder>() {
    private var typeList = TypeIncomeList.data
    private lateinit var mContext : Context

    inner class ViewHolder(view : CardIncomeBinding) : RecyclerView.ViewHolder(view.root) {
        val getIncome = view.tvIncome
        val getTaxPaid = view.tvTaxPaid
        val getTaxType = view.tvIncomeType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        return ViewHolder(CardIncomeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.getIncome.text = "${incomeDataList[position].income}  บาท"
        holder.getTaxPaid.text = "${incomeDataList[position].incomeVAT}  บาท"
        holder.getTaxType.text = typeList[incomeDataList[position].type].description

        holder.itemView.rootView.setOnClickListener {
            (mContext as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, InfoFragment.newInstance("",""))
                .addToBackStack("info")
                .commit();
        }
    }

    override fun getItemCount(): Int {
        return incomeDataList.size
    }

    fun updateList(incomeList : List<IncomeData>) {
        incomeDataList = incomeList
        notifyDataSetChanged()
    }
}
