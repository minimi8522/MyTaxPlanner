package com.example.mytaxplanner.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class IncomeDataEntity(val income : Double , val incomeVAT : Double){

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0
}
