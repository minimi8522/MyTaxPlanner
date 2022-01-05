# MyTaxPlanner
 My Tax Planner เป็นแอปพลิเคชั่นสำหรับการคำนวณภาษี สำหรับปี พ.ศ.2564
จัดทำขึ้นเพื่อเป็นส่วนนึงในการทำโครงการพิเศษ

## การติดตั้ง
ดาวน์โหลดไฟล์ .zip ที่ระบุไว้ แล้วทำการ unzip
แล้วทำการเปิดโปรแกรม Android Studio แล้วทำการImport Fileเพื่อใช้งาน

```bash
File > Open > เลือกFolder ที่เป็นชื่อไฟล์ที่ Unzip
```

## การใช้งาน
 ทำการ Run โปรแกรมบน Emulator ในโปรแกรมAndroid Studio 
หรือ สร้างไฟล์ APK เพื่อนำไปติดตั้งในอุปกรณ์เคลื่อนที่ 
สามารถสร้างไฟล์ APK ได้ดังนี้ 

```bash
Build > Build Bundle(S) / APK(s) > Build APK(s)
```

แล้วนำไฟล์ APK ที่ได้ไปติดตั้งในอุปกรณ์เคลื่อนที่เพื่อใช้งาน

## โครงสร้าง
```bash
app
├── manifests                  
├── java          #Source Code การทำงานต่างๆ 'src'                                
├── res           #เครื่องมือ และโครงสร้างหน้าจอ ที่ใช้งานในโปรแกรมนี้                         
└── README.md
```

### Java
```bash
Java
├── com.example.mytaxplanner
│   ├── adapter        #ส่วนเก็บ src ในการแสดงผล
│   ├── fragment       #ส่วนเก็บ src ในการทำงาน
│   ├── model          #ส่วนเก็บ data class
│   ├── repository     #ส่วนการใช้งานเกี่ยวกับ data base
│   ├── util           #ส่วนเก็บ src ประเภทutility
│   ├── viewmodel    
│   └── Main Activity
└── ...
```
