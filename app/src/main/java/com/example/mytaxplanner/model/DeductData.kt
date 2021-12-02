package com.example.mytaxplanner.model

data class DeductData (val deductionType : Int ,val deduction : Double){
    val deductionMax : Double
        get() {
            return 0.0
        }

}