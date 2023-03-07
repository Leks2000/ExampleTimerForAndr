package com.example.testunit

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvmrec  = findViewById(R.id.TVRec)
        tvavg = findViewById(R.id.TVAvg)
        tvmax = findViewById(R.id.TVMax)
        btnStart = findViewById(R.id.buttonStart)
        btncor = findViewById(R.id.BtnCorrect)
        btnIncor = findViewById(R.id.BtnIncorrect)
        mEdit = findViewById(R.id.TVResult)
        allTest = findViewById(R.id.ressultOfAllEx)
        correct = findViewById(R.id.Correct)
        inCorrect = findViewById(R.id.Incorrect)
        exampleLine = findViewById(R.id.ExampleLine)
        percent = findViewById(R.id.ResultInPercent)
        btncor.isEnabled = false
        btnIncor.isEnabled = false
    }
    private var result = ""
    private lateinit var percent: TextView
    private lateinit var allTest: TextView
    private lateinit var correct: TextView
    private lateinit var inCorrect: TextView
    private lateinit var tvavg: TextView
    private lateinit var tvmrec: TextView
    lateinit var tvmax: TextView
    private lateinit var mEdit: TextView
    private lateinit var btncor:Button
    private lateinit var btnStart:Button
    private lateinit var btnIncor: Button
    private lateinit var exampleLine: LinearLayout
    private var count = 0
    private var countCorrect = 0
    private var countInCorrect = 0
    private var alltime = 0
    private var countForAvg = 0
    private fun createExample(){
        val mEdit = findViewById<TextView>(R.id.TVResult)
        val firstText: TextView = findViewById(R.id.TextFirstOperand)
        val secondText: TextView = findViewById(R.id.TextSecondOperand)
        val operationText: TextView = findViewById(R.id.Operation)
        val firstNum = (10..99).random()
        val secNum = (10..99).random()
        val randSign = (1..4).random()
        when(randSign){
            1 -> operationText.text = "*"
            2 -> operationText.text = "/"
            3 -> operationText.text = "+"
            4 -> operationText.text = "-"
        }
        if(randSign == 1){
            result = (firstNum * secNum).toString()
        }
        if(randSign == 2){
            result = (firstNum.toBigDecimal().divide(secNum.toBigDecimal(), 2, RoundingMode.HALF_UP)).toString()
        }
        if(randSign == 3){
            result = (firstNum + secNum).toString()

        }
        if(randSign == 4){
            result = (firstNum - secNum).toString()
        }
        val randForAnswer = (1..2).random()
        if (randForAnswer == 1){
            mEdit.text = result
        }
        if (randForAnswer == 2){
            mEdit.text = (10..99).random().toString()
        }
        secondText.text = secNum.toString()
        firstText.text = firstNum.toString()
    }
    fun corButton(view: View){
        count++
        allTest.text = count.toString()
        if (result == mEdit.text.toString()){
            exampleLine.setBackgroundColor(Color.parseColor("#FF00FF00"))
            countCorrect++
            correct.text = countCorrect.toString()
            recordSecond()
        }else{
            exampleLine.setBackgroundColor(Color.parseColor("#FFFF0000"))
            countInCorrect++
            inCorrect.text = countInCorrect.toString()
        }
        forAvgTime()
        reboot()
        stopButton()
    }
    private fun forAvgTime(){
        alltime += counter
        countForAvg++
        tvavg.text = ((alltime/countForAvg)*100).toString()
    }
    @SuppressLint("SetTextI18n")
    private fun reboot(){
        val formfar = if (countCorrect == 0){
            DecimalFormat("#.#")
        } else{
            DecimalFormat("#.0")
        }
        percent.text = formfar.format(countCorrect.toBigDecimal().divide(count.toBigDecimal(), 3, RoundingMode.HALF_UP)*100.toBigDecimal()) + "%"
    }
    fun startButton(view: View){
        timerStart()
        btncor.isEnabled = true
        btnIncor.isEnabled = true
        createExample()
        btnStart.isEnabled = false
    }
    private fun stopButton(){
        timerStop()
        btnStart.isEnabled = true
        btncor.isEnabled = false
        btnIncor.isEnabled = false
    }
    fun inCor(view: View){
        val allTest: TextView = findViewById(R.id.ressultOfAllEx)
        val correct: TextView = findViewById(R.id.Correct)
        val inCorrect: TextView = findViewById(R.id.Incorrect)
        count++
        allTest.text = count.toString()
        if (result !== mEdit.text.toString()){
            exampleLine.setBackgroundColor(Color.parseColor("#FF00FF00"))
            countCorrect++
            correct.text = countCorrect.toString()
            recordSecond()
        }else{
            exampleLine.setBackgroundColor(Color.parseColor("#FFFF0000"))
            countInCorrect++
            inCorrect.text = countInCorrect.toString()
        }
        forAvgTime()
        reboot()
        stopButton()
    }
    var counter = 0
    private var record = 60
    private val timer = object: CountDownTimer(50000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            counter++
            tvmax.text=("$counter")
        }

        override fun onFinish() {
        }
    }
        private fun timerStart(){
            timer.start()
        }
        private fun timerStop(){
            timer.cancel()
            counter = 0
        }
    private fun recordSecond(){
        if(record > counter) {
            record = counter
            tvmrec.text = record.toString()
        }
    }
}
