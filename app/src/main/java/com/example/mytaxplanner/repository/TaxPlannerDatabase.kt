package com.example.mytaxplanner.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mytaxplanner.model.entity.IncomeDataEntity

@Database(entities = [IncomeDataEntity::class], version = 1, exportSchema = false)
abstract class TaxPlannerDatabase : RoomDatabase() {

    abstract val taxPlannerDao: TaxPlannerDao

    companion object {

        @Volatile
        private var INSTANCE: TaxPlannerDatabase? = null

        fun getInstance(context: Context): TaxPlannerDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaxPlannerDatabase::class.java,
                        "tax_planner_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}