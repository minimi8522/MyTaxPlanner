package com.example.mytaxplanner.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.mytaxplanner.databinding.CardDeductBinding
import com.example.mytaxplanner.model.DeductData
import com.example.mytaxplanner.model.IncomeData
import com.example.mytaxplanner.model.TypeDeductList

class DeductAdapter(var deductDataList: List<DeductData>) : RecyclerView.Adapter<DeductAdapter.ViewHolder>() {
    private var typeList = TypeDeductList.data

    inner class ViewHolder(view: CardDeductBinding) : RecyclerView.ViewHolder(view.root) {
        val getDeduct = view.tvDeduct
        //val getMax = view.tvMaxDeduct
        val getDeductType = view.tvDeductType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardDeductBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.getDeduct.text = "${deductDataList[position].deduction} บาท"
        //holder.getMax.text = "${deductDataList[position].deductionMax} บาท"
        holder.getDeductType.text = typeList[deductDataList[position].deductionType].description
    }

    override fun getItemCount(): Int {
        return deductDataList.size
    }

    fun updateList(deductList : List<DeductData>) {
        deductDataList = deductList
        notifyDataSetChanged()
    }
}
