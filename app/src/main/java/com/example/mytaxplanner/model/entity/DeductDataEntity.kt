package com.example.mytaxplanner.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deduct_table")
data class DeductDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val deductType : Int,
    val deduction : Double,
    val deductionMax : Double)

