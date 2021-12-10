package com.example.mytaxplanner.model

data class DeductData (val deductionType : Int ,val deduction : Double , val deductionMax : Double){
}

data class DeductType(val type : Int, val description : String, val deductionMax: Double)

object TypeDeductList {
    val data = listOf(
        //0-4 พิการ เพิ้่มค่าดูแลคนพิการ60000
        DeductType(0,"ส่วนตัว",60000.0),
        DeductType(1,"พ่อ / พ่อคู่สมรส",30000.0),
        DeductType(2,"แม่ / แม่คู่สมรส",30000.0),
        DeductType(3,"คู่สมรส ",60000.0),
        DeductType(4,"ลูกแท้ๆ",60000.0),/*ลูกแท้ๆ เกิน25ต้องไร้ความสามารถ อายุไม่เกิน25ปี ก่อน2018 30000ทุกคน หลังคนแรก30000 คนถัดไป60000*/
        DeductType(5,"ลูกบุญธรรม",30000.0),/*นับทุกคนไม่เกินคน3 */
        DeductType(6,"ผู้พิการ/ทุพพลภาพ",60000.0),/*ได้คนเดียว*/
        DeductType(7,"ฝากครรภ์",60000.0),

        DeductType(8,"ประกันสังคม ",5100.0),/*ของปี64*/
        DeductType(9,"ประกันชีวิต ",100000.0),
        DeductType(10,"ประกันชีวิตแบบบำนาญ ",200000.0),/*15% ของคำนวณ*/
        DeductType(11,"เบี้ยประกันตนเอง",25000.0),
        DeductType(12,"เบี้ยประกันพ่อแม่ ",15000.0),
        DeductType(13,"เบี้ยประกันพ่อแม่คู่สมรส ",15000.0),

        //14-19 รวมกันได้ไม่เกิน500000
        DeductType(14,"RMF ",500000.0),/*ได้30% ของคำนวณ*/
        DeductType(15,"SSF ",200000.0),/*ได้30% ของคำนวณ*/
        DeductType(16,"กองทุนสำรองเลี้ยงชีพ",500000.0),/*15% ของคำนวณ*/
        DeductType(17,"กองทุน กบข. ",500000.0),
        DeductType(18,"กองทุนสงเคราะห์ครูเอกชน ",500000.0),
        DeductType(19,"กองทุนการออมแห่งชาติ ",13200.0),

        DeductType(20,"บริจาคพรรคการเมือง ",10000.0),
        DeductType(21,"บริจาคการศึกษา กีฬา และโรงพยายาม ",0.0),/* 2เท่าของที่กรอก ไม่เกิน10%*/
        DeductType(22,"บริจาคปกติ ",60000.0),/*ไม่เกิน10%*/

        DeductType(23,"ดอกเบี้ยบ้าน ",100000.0)
    )
}



