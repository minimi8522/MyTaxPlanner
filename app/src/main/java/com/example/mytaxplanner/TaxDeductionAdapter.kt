package com.example.mytaxplanner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytaxplanner.databinding.TaxBarBinding

class TaxDeductionAdapter(var listTax: ArrayList<TaxDeductionData>) : RecyclerView.Adapter<TaxDeductionAdapter.ViewHolder>() {
    class ViewHolder(itemView: TaxBarBinding) : RecyclerView.ViewHolder(itemView.root){
        val nameText = itemView.nameText
        val value = itemView.valueDouble
        val maxValue = itemView.maxValueDouble
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TaxBarBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameText.text = listTax[position].name
        holder.value.text = listTax[position].value.toString()
        holder.maxValue.text = listTax[position].maxValue.toString()
        if (listTax[position].type == 1){
            holder.value.text = ""
            holder.maxValue.text = ""
        }
    }

    override fun getItemCount(): Int {
        return listTax.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (listTax[position].type == 1){
            1
        }else{
            2
        }
    }
}