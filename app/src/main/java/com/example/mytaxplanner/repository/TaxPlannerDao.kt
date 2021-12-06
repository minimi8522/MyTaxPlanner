package com.example.mytaxplanner.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mytaxplanner.model.entity.DeductDataEntity
import com.example.mytaxplanner.model.entity.IncomeDataEntity

@Dao
interface TaxPlannerDao {

    @Insert
    fun insertIncomeData(incomeData : IncomeDataEntity)

    @Insert
    fun insertDeductData(deductData : DeductDataEntity)

    @Query("SELECT * FROM income_table")
    fun getIncome() : LiveData<List<IncomeDataEntity>>

    @Query("SELECT * FROM deduct_table")
    fun getDeduct() : LiveData<List<DeductDataEntity>>

    @Query("DELETE FROM income_table WHERE id = :index ")
    fun deleteIncome(index :Int)

    @Query("DELETE FROM deduct_table WHERE id = :index")
    fun deleteDeduct(index :Int)

}