package com.example.mytaxplanner

import androidx.room.Entity

@Entity(tableName = "Tax_Table")
data class TaxDeductionData(var id:Int,var name:String,var value:Double,var maxValue:Double,var type:Int)
