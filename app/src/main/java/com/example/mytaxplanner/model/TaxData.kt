package com.example.mytaxplanner.model

import androidx.room.Entity

@Entity(tableName = "Tax_Table")
data class TaxData(
    var name: String = "",
    var value: Double = 0.00,
    var maxValue: Double = 0.00,
    var type: Int = 0,

    var income: Int = 0,
    var taxPaid: Int = 0,
    var deduction: Int = 0,
    var taxPrice: Int = 0
){
    companion object {
        const val LV0 = 0
        const val LV1 = 5
        const val LV2 = 10
        const val LV3 = 15
        const val LV4 = 20
        const val LV5 = 25
        const val LV6 = 30
        const val LV7 = 35
    }
}
