package com.example.mytaxplanner.repository

import androidx.room.Insert
import com.example.mytaxplanner.model.entity.IncomeDataEntity

interface TaxPlannerDao {

    @Insert
    fun InsertIncomeData(incomeData : IncomeDataEntity)
}