package com.example.mytaxplanner.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.mytaxplanner.repository.TaxPlannerDatabase

class CheckSuggestionService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val db = TaxPlannerDatabase.getInstance(this).taxPlannerDao
        val deductData = db.getDeduct()
        deductData.value?.forEachIndexed { index, deductDataEntity ->

        }
        return super.onStartCommand(intent, flags, startId)


    }
}