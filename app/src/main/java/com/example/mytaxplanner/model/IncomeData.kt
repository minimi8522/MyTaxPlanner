package com.example.mytaxplanner.model

data class IncomeData(val type : Int ,val income : Double , val incomeVAT : Double)

data class IncomeType(val type : Int, val description : String)

object TypeIncomeList{
    val data = listOf(
        //0-1 ค่าใช้จ่าย50% ไม่เกิน100000
        IncomeType(0,"เงินเดือน และโบนัส "),
        IncomeType(1,"ค่านายหน้า และฟรีแลนซ์ "),

        //2 ค่าใช้จ่าย50% ไม่เกิน100000
        IncomeType(2,"ค่าลิขสิทธิ์ "),
        IncomeType(3,"เงินจากคำพิพากษาศาล "),

        IncomeType(4,"ดอกเบี้ย และเงินปันผล "),

        //5-9 หักตามจริงต้องมีหลักฐาน หรือหักเหมา
        IncomeType(5,"ค่าเช่าอสังหาริมทรัพย์ "), /*30%*/
        IncomeType(6,"ค่าเช่าที่ดินเกษตร "),/*20%*/
        IncomeType(7,"ค่าเช่าที่ดิน "),/*15%*/
        IncomeType(8,"ค่าเช่ายานพาหนะ "),/*30%*/
        IncomeType(9,"ค่าเช่าอื่นๆ "),/*10%*/
        IncomeType(10,"ค่าผิดสัญญา "),/*ได้แค่เหมา20%*/

        //11-12 ตามจริงมีหลักฐาน หรือเหมา
        IncomeType(11,"ค่าวิชาชีพประกอบโรคศิลป์ "),/*60%*/
        IncomeType(12,"ค่าวิชาชีพอิสระตามกฏหมาย "),/*30%*/

        //13 หักตามจริงมีหลักฐาน หรือเหมา60เปอ
        IncomeType(13,"ค่ารับเหมา "),

        //อื่นๆ 15+ #หักตามจริงมีหลักฐาน หรือเหมา
        IncomeType(14,"เงินปันผลกองทุนรวมตามประกาศคณะปฏิวัติ"),
        IncomeType(15,"ขายอสังหาริมทรัพย์ที่ได้รับมาจากผู้อื่น หรือมรดก "),/*เหมา50% นอกตัวเมือง ยกเว้น200000*/
        IncomeType(16,"ขายอสังหาริมทรัพย์ที่ไม่มุ่งหวังผลกำไร "),/* #หรือเหมาตามปีที่ครอบครอง 50-92% */
        IncomeType(17,"นักแสดง "),/* # หรือ 60%สำหรับ300000แรก และ40%สำหรับส่วนเกิน รวมไม่เกิน600000*/
        IncomeType(18,"การขายที่ดินเงินผ่อน หรือ ให้เช่าซื้อที่ดิน "),/* # หรือเหมา60% */
        IncomeType(19,"รายได้จากธุรกิจ "),/* # หรือเหมา60% */
        IncomeType(20,"อื่นๆ ")/* # */
    )
}