package com.example.mytaxplanner.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytaxplanner.databinding.CardDeductBinding
import com.example.mytaxplanner.databinding.CardSuggestBinding
import com.example.mytaxplanner.model.DeductData
import com.example.mytaxplanner.model.DeductType
import com.example.mytaxplanner.model.TypeDeductList

class SuggestAdapter(var suggestDataList: List<DeductType>) : RecyclerView.Adapter<SuggestAdapter.ViewHolder>() {
    private var typeList = TypeDeductList.data

    inner class ViewHolder(view: CardSuggestBinding) : RecyclerView.ViewHolder(view.root) {
        val getDeduct = view.tvDeduct
        val getDeductType = view.tvDeductType
    }



    fun updateList(typeList : List<DeductType>) {
        suggestDataList = typeList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardSuggestBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.getDeduct.text = suggestDataList[position].deductionMax.toString()
        holder.getDeductType.text = suggestDataList[position].description
    }

    override fun getItemCount(): Int {
        return suggestDataList.size
    }
}