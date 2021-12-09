package com.example.mytaxplanner.model

data class DeductData (val deductionType : Int ,val deduction : Double , val deductionMax : Double){
}

data class DeductType(val type : Int, val description : String, val deductionMax: Double)

object TypeDeductList {
    val data = listOf(
        //0-4 พิการ เพิ้่มค่าดูแลคนพิการ60000
        DeductType(0,"ส่วนตีว",60000.0),
        DeductType(1,"พ่อ",30000.0),
        DeductType(2,"แม่",30000.0),
        DeductType(1,"คู่สมรส ",60000.0),
        DeductType(3,"ลูกแท้ๆ",60000.0),/*ลูกแท้ๆ เกิน25ต้องไร้ความสามารถ อายุไม่เกิน25ปี ก่อน2018 30000ทุกคน หลังคนแรก30000 คนถัดไป60000*/
        DeductType(4,"ลูกบุญธรรม",30000.0),/*นับทุกคนไม่เกินคน3 */
        DeductType(5,"ผู้พิการ/ทุพพลภาพ",60000.0),/*ได้คนเดียว*/

        DeductType(6,"ประกันสังคม ",5100.0),/*ของปี64*/
        DeductType(7,"ประกันชีวิต ",100000.0),
        DeductType(8,"ประกันชีวิตแบบบำนาญ ",200000.0),/*15% ของคำนวณ*/
        DeductType(9,"เบี้ยประกันตนเอง",25000.0),
        DeductType(10,"เบี้ยประกันพ่อแม่ ",15000.0),

        //11-16 รวมกันได้ไม่เกิน500000
        DeductType(11,"RMF ",500000.0),/*ได้30% ของคำนวณ*/
        DeductType(12,"SSF ",200000.0),/*ได้30% ของคำนวณ*/
        DeductType(13,"กองทุนสำรองเลี้ยงชีพ",500000.0),/*15% ของคำนวณ*/
        DeductType(14,"กองทุน กบข. ",500000.0),
        DeductType(15,"กองทุนสงเคราะห์ครูเอกชน ",500000.0),
        DeductType(16,"กองทุนการออมแห่งชาติ ",13200.0),

        DeductType(17,"บริจาคพรรคการเมือง ",10000.0),
        DeductType(18,"บริจาคการศึกษา กีฬา และโรงพยายาม ",0.0),/* 2เท่าของที่กรอก ไม่เกิน10%*/
        DeductType(19,"บริจาคปกติ ",60000.0),/*ไม่เกิน10%*/

        DeductType(20,"ดอกเบี้ยบ้าน ",100000.0)
    )
}

