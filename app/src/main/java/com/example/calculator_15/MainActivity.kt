package com.example.calculator_15

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    //ประกาศตัวแปร
    private lateinit var display: TextView //แสดงผลลัพธ์/แสดงตัวเลข
    private var current = "" //ค่าปัจจุุบันที่กำลังพิมพื
    private var operator = ""//เครื่องหมายที่เราเลือก (+,-,*,/)
    private var fristNumber = 0.0//ตัวเลขแรก
    private var secondNumber = 0.0//ตัวเลขตัวที่ 2
    private var result = 0.0 //ผลลัพธ์
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        display = findViewById(R.id.Display)

        // ตัวแปรเก็บตัวเลข
        val numberButtons = listOf(
            R.id.btn0,
            R.id.btn1,
            R.id.btn2,
            R.id.btn3,
            R.id.btn4,
            R.id.btn5,
            R.id.btn6,
            R.id.btn7,
            R.id.btn8,
            R.id.btn9
        )
            //  กำหมด Event ให้กับตัวเลข
        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener {
                numberClicked((it as Button).text.toString())
            }
        }
        //ปุ่มเครื่องหม่ายพื้นฐาน
        findViewById<Button>(R.id.btnPlus).setOnClickListener { operatorClicked("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { operatorClicked("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { operatorClicked("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { operatorClicked("/") }
        findViewById<Button>(R.id.btnMod).setOnClickListener { operatorClicked("%") }

        //ปุ่มเครื่องหมายพิเศษ
        findViewById<Button>(R.id.btnequal).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btncomma).setOnClickListener { addDot() }
        findViewById<Button>(R.id.btnPlusminussign).setOnClickListener { toggleSign() }
        findViewById<Button>(R.id.btnDelete).setOnClickListener { backSpace() }

    }
    // เมื่อกดปุ่มตัวเลข
    private fun numberClicked(value: String) {
        if (isNewOperation) { //ถ้าเป็ฯการเริ่มใหม่ ให้ทำการล้างหน้าจอก่อน
            display.text = " "
            isNewOperation = false
        }
        current += value//เพิ่มค่าตัวเลขที่ต่อท้าย
        display.text = current //ให้แสดงค่าบนหน้าจอ
    }

    //ปุ่มเครื่อ
    private fun operatorClicked(op: String) {
        if (current.isNotEmpty()) {//ต้องมีตัวเลขก่อน ถึงจะเลือกเครื่องหมายได
            fristNumber = current.toDouble()
            operator = op
            current = "" //เคลีนหน้าจอเพื่อรอรับตัวเลขถัดไป
        }
    }
    //เมื่อกดปุ่มเท่ากับ(คำนวณ))
    private fun  calculateResult() {
        if (current.isNotEmpty()){
            secondNumber = current.toDouble()
            result = when (operator) {
                "+" -> fristNumber + secondNumber
                "-" -> fristNumber - secondNumber
                "*" -> fristNumber * secondNumber
                "/" -> if (secondNumber != 0.0) fristNumber / secondNumber else Double.NaN
                "%" -> if (secondNumber != 0.0) fristNumber % secondNumber else Double.NaN
                else -> 0.0
            }
            //ถ้าหาร0ด้วยแสดงข้อความว่า Cannot divide by zero
            display.text = if (result.isNaN()) "Cannot divide by zero" else result.toString()
            current = result.toString()
            isNewOperation = true
        }
    }
    //เมื่อต้องการเคลียข้อมูลทั้งหมด
    private fun clearAll() {
        current = ""
        operator = ""
        fristNumber = 0.0
        secondNumber = 0.0
        result = 0.0
        display.text = "0"
        isNewOperation = true
    }
    //เพิ่มจุดทศนิยมในชุดตัวเลข
    private fun  addDot(){
        if (!current.contains(".")){
            if (current.isEmpty()) current = "0"
            current += "."
            display.text = current
        }
    }
    //ปุ่มเปลี่ยนเครื่องหมาย (+) -> (-), (-) -> (+)
    private fun  toggleSign (){
        if (current.isNotEmpty() && current != "0"){
            current = if (current.startsWith("-")){
                current.substring(1)
            } else {
                "-$current"
            }
            display.text = current
        }
    }
    //ปุ่มลบทีละตัว
    private  fun  backSpace() {
        if (isNewOperation) return

        if (current.isNotEmpty()) {
            current = current.dropLast(1)//ตัดตัวท้ายออกทีละตัว

            if (current == "-" || current.isEmpty()) {
                current = ""
                display.text = "0"
            } else {
                display.text = current
            }
        }
    }
}




