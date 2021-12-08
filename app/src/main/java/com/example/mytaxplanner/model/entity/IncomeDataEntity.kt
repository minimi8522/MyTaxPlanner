package com.example.mytaxplanner.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income_table")
data class IncomeDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val type : Int,
    val income : Double,
    val incomeVAT : Double)

