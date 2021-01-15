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


    private lateinit var nextDate: Unit
    private lateinit var nextDay: String
    private lateinit var nextDateAsArray: List<String>
    private lateinit var dayNumberAndMonthArray: List<String>
    private lateinit var calendarDayButton: FrameLayout
    private lateinit var listView: ListView
    private lateinit var sp: SharedPreferences

    private lateinit var layout: LinearLayout
    private lateinit var btnCalendarDay1: FrameLayout
    private lateinit var btnCalendarDay2: FrameLayout
    private lateinit var btnCalendarDay3: FrameLayout
    private lateinit var btnCalendarDay4: FrameLayout
    private lateinit var btnCalendarDay5: FrameLayout
    private lateinit var btnCalendarDay6: FrameLayout

    private var dayOfWeekAsInt by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mycalendar)

        sp = getSharedPreferences("sh_pref", MODE_PRIVATE)
        initViews()
        setNextDayDate()
        buttonInit()
        if (sp.getBoolean("sw_low", true) ||
            sp.getBoolean("sw_med", true) ||
            sp.getBoolean("sw_hig", true)
        ) {
            takePlan(calendar.time.day)
        } else {
            layout.visibility = View.GONE
        }

        setCalendarButtonsUnchecked()

        btnCalendarDay1.setOnClickListener {
            setCalendarButtonsUnchecked()
            takePlan(calendar.time.day)
            btnCalendarDay1.isEnabled = false
        }
        btnCalendarDay2.setOnClickListener {
            setCalendarButtonsUnchecked()
            nextDate = calendar.add(Calendar.DATE, 1)
            takePlan(calendar.time.day)
            nextDate = calendar.add(Calendar.DATE, -1)
            btnCalendarDay2.isEnabled = false


        }
        btnCalendarDay3.setOnClickListener {
            setCalendarButtonsUnchecked()
            nextDate = calendar.add(Calendar.DATE, 2)
            takePlan(calendar.time.day)
            nextDate = calendar.add(Calendar.DATE, -2)
            btnCalendarDay3.isEnabled = false

        }
        btnCalendarDay4.setOnClickListener {
            setCalendarButtonsUnchecked()
            nextDate = calendar.add(Calendar.DATE, 3)
            takePlan(calendar.time.day)
            nextDate = calendar.add(Calendar.DATE, -3)
            btnCalendarDay4.isEnabled = false
        }
        btnCalendarDay5.setOnClickListener {
            setCalendarButtonsUnchecked()
            nextDate = calendar.add(Calendar.DATE, 4)
            takePlan(calendar.time.day)
            nextDate = calendar.add(Calendar.DATE, -4)
            btnCalendarDay5.isEnabled = false
        }
        btnCalendarDay6.setOnClickListener {
            setCalendarButtonsUnchecked()
            nextDate = calendar.add(Calendar.DATE, 5)
            takePlan(calendar.time.day)
            nextDate = calendar.add(Calendar.DATE, -5)
            btnCalendarDay6.isEnabled = false

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

                    monthAsString.text = dayNumberAndMonthArray[2]

                    setNextDayDate()
                }
            }
            nextDate = calendar.add(Calendar.DATE, -7)
        } catch (e: Exception) {

        }
    }

    private fun setCalendarButtonsUnchecked() {
        btnCalendarDay1.isEnabled = true
        btnCalendarDay2.isEnabled = true
        btnCalendarDay3.isEnabled = true
        btnCalendarDay4.isEnabled = true
        btnCalendarDay5.isEnabled = true
        btnCalendarDay6.isEnabled = true
    }


    private fun takePlan(day: Int) {

        val taskList = takePlanDay(day, sp, this)

        try {
            listView.adapter = TaskAdapter(this, taskList, R.layout.task_view)


        } catch (e: Exception) {
            listView.visibility = View.GONE
        }
    }

    private fun initViews() {
        btnCalendarDay1 = findViewById(R.id.frame_button)
        btnCalendarDay2 = findViewById(R.id.frame_button2)
        btnCalendarDay3 = findViewById(R.id.frame_button3)
        btnCalendarDay4 = findViewById(R.id.frame_button4)
        btnCalendarDay5 = findViewById(R.id.frame_button5)
        btnCalendarDay6 = findViewById(R.id.frame_button6)
        listView = findViewById(R.id.lv_task_plan)
        calendarDayButton = findViewById(R.id.frame_button)
        layout = findViewById(R.id.button_plan_layout)
        dbhandler = Task_DB_Helper(this)
        layout.visibility = View.VISIBLE

    }

    private fun setNextDayDate() {

        nextDay = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        dayOfWeekAsInt = calendar.time.day
        nextDateAsArray = nextDay.split(",")
        dayNumberAndMonthArray = nextDateAsArray[1].split(" ")
        nextDate = calendar.add(Calendar.DATE, 1)
    }
}