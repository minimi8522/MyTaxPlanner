package com.example.mytaxplanner.model

data class DeductData (val deductionType : Int ,val deduction : Double){
    val deductionMax : Double
        get() {
            return 0.0
        }

}

object TypeDeductList {
    val data = listOf(
        DeductType(0,"เงินเดือน"),
        DeductType(1,"ค่าจ้าง")


    )
}

data class DeductType(val type : Int, val description : String)