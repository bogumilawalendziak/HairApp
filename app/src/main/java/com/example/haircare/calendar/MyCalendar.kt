package com.example.haircare.calendar

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.haircare.MainActivity.Companion.takePlanDay
import com.example.haircare.R
import java.text.DateFormat
import java.util.*
import kotlin.properties.Delegates

class MyCalendar : AppCompatActivity() {

    var dbhandler: Task_DB_Helper? = null
    var name: String? = null
    var product: String? = null

    val calendar: Calendar = Calendar.getInstance()
    private val dateNow = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time).toLowerCase()

    private lateinit var nextDate: Unit
    private lateinit var nextDay: String
    private lateinit var nextDateAsArray: List<String>
    private lateinit var dayNumberAndMonthArray: List<String>
    private lateinit var framelayout: FrameLayout
    private lateinit var listView: ListView
    private lateinit var sp: SharedPreferences

    private lateinit var layout: LinearLayout
    private lateinit var button1: FrameLayout
    private lateinit var button2: FrameLayout
    private lateinit var button3: FrameLayout
    private lateinit var button4: FrameLayout
    private lateinit var button5: FrameLayout
    private lateinit var button6: FrameLayout

    private var dayOfWeekAsInt by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mycalendar)

        sp = getSharedPreferences("sh_pref", MODE_PRIVATE)

        initViews()
        dataInit()
        buttonInit()
        if (sp.getBoolean("sw_low", true) ||
            sp.getBoolean("sw_med", true) ||
            sp.getBoolean("sw_hig", true)
        ) {
            takePlan(calendar.time.day)
        } else {
            layout.visibility = View.GONE
        }

        buttonClear()

        button1.setOnClickListener {
            buttonClear()

            takePlan(calendar.time.day)
            button1.isEnabled = false
            println("*****clicked********")

        }
        button2.setOnClickListener {
            buttonClear()
            nextDate = calendar.add(Calendar.DATE, 1)
            takePlan(calendar.time.day)
            nextDate = calendar.add(Calendar.DATE, -1)
            button2.isEnabled = false

            println("*****clicked********")
        }
        button3.setOnClickListener {
            buttonClear()
            nextDate = calendar.add(Calendar.DATE, 2)
            takePlan(calendar.time.day)
            nextDate = calendar.add(Calendar.DATE, -2)
            button3.isEnabled = false
            println("*****clicked********")
        }
        button4.setOnClickListener {
            buttonClear()
            nextDate = calendar.add(Calendar.DATE, 3)
            takePlan(calendar.time.day)
            nextDate = calendar.add(Calendar.DATE, -3)
            println("*****clicked********")
            button4.isEnabled = false
        }
        button5.setOnClickListener {
            buttonClear()
            nextDate = calendar.add(Calendar.DATE, 4)
            takePlan(calendar.time.day)
            nextDate = calendar.add(Calendar.DATE, -4)
            button5.isEnabled = false
        }
        button6.setOnClickListener {
            buttonClear()
            nextDate = calendar.add(Calendar.DATE, 5)
            takePlan(calendar.time.day)
            nextDate = calendar.add(Calendar.DATE, -5)
            button6.isEnabled = false

        }
    }

    private fun buttonInit() {
        try {
            for (i in 0 until layout.childCount) {
                val v: View = layout.getChildAt(i)

                if (v is FrameLayout) {
                    val date = v.findViewById<TextView>(R.id.tv_day_button)
                    val dayAsString = v.findViewById<TextView>(R.id.frame_button_text)
                    val monthAsString = v.findViewById<TextView>(R.id.tv_calendar_month)
                    date.text = nextDateAsArray[0]
                    dayAsString.text = dayNumberAndMonthArray[1]
                    println(dayNumberAndMonthArray[1])
                    monthAsString.text = dayNumberAndMonthArray[2]
                    println(dayNumberAndMonthArray[2])
                    dataInit()
                }
            }
            nextDate = calendar.add(Calendar.DATE, -7)
        } catch (e: Exception) {

        }
    }

    private fun buttonClear() {
        button1.isEnabled = true
        button2.isEnabled = true
        button3.isEnabled = true
        button4.isEnabled = true
        button5.isEnabled = true
        button6.isEnabled = true
        println("Clear")

    }


    private fun takePlan(day: Int) {
        val date = dateNow.split(",")
        val dayOfWeek = date[0]
        val monthValue = date[1]
        val taskList = takePlanDay(day, sp, this)

        try {
            listView.adapter = TaskAdapter(this, taskList, R.layout.task_view)


        } catch (e: Exception) {
            listView.visibility = View.GONE
        }
    }

    private fun initViews() {
        button1 = findViewById(R.id.frame_button)
        button2 = findViewById(R.id.frame_button2)
        button3 = findViewById(R.id.frame_button3)
        button4 = findViewById(R.id.frame_button4)
        button5 = findViewById(R.id.frame_button5)
        button6 = findViewById(R.id.frame_button6)
        listView = findViewById(R.id.lv_task_plan)
        framelayout = findViewById(R.id.frame_button)
        layout = findViewById(R.id.button_plan_layout)
        dbhandler = Task_DB_Helper(this)
        layout.visibility = View.VISIBLE

        // buttonreal = findViewById(R.id.button_real)
        // buttonreal2 = findViewById(R.id.button_real2)
    }

    private fun dataInit() {

        nextDay = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        dayOfWeekAsInt = calendar.time.day
        nextDateAsArray = nextDay.split(",")
        dayNumberAndMonthArray = nextDateAsArray[1].split(" ")
        nextDate = calendar.add(Calendar.DATE, 1)
    }
}