package com.example.haircare.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haircare.R
import com.example.haircare.customplan.CreateCustomPlan
import kotlinx.android.synthetic.main.activity_mycalendar.*
import java.text.DateFormat
import java.util.*

class MyCalendar : AppCompatActivity() {

    var databaseHandler: CustomTaskAdapter? = null
    var name: String? = null
    var product: String? = null

    private val calendar: Calendar = Calendar.getInstance()


    private lateinit var nextDate: Unit
    private lateinit var nextDay: String

    private lateinit var dayOfWeek: String
    private lateinit var dayNumber: String
    private lateinit var calendarDayButton: FrameLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var layout: LinearLayout
    private lateinit var btnCalendarDay1: FrameLayout
    private lateinit var btnCalendarDay2: FrameLayout
    private lateinit var btnCalendarDay3: FrameLayout
    private lateinit var btnCalendarDay4: FrameLayout
    private lateinit var btnCalendarDay5: FrameLayout
    private lateinit var btnCalendarDay6: FrameLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mycalendar)

        initViews()
        setNextDayDate()
        setDate(0)
        buttonInit()
        setCalendarButtonsUnchecked()

        btnCalendarDay1.setOnClickListener {
            setDate(0)
            btnCalendarDay1.isEnabled = false
        }
        btnCalendarDay2.setOnClickListener {
            setDate(1)
            btnCalendarDay2.isEnabled = false
        }
        btnCalendarDay3.setOnClickListener {

            setDate(2)
            btnCalendarDay3.isEnabled = false
        }
        btnCalendarDay4.setOnClickListener {
            setDate(3)
            btnCalendarDay4.isEnabled = false
        }
        btnCalendarDay5.setOnClickListener {
            setDate(4)
            btnCalendarDay5.isEnabled = false
        }
        btnCalendarDay6.setOnClickListener {
            setDate(5)
            btnCalendarDay6.isEnabled = false
        }

        btn_calendar_create_task.setOnClickListener {
            startActivity(Intent(this, CreateCustomPlan::class.java))
        }
    }


    private fun buttonInit() {
        try {
            for (i in 0 until layout.childCount) {
                val v: View = layout.getChildAt(i)

                if (v is FrameLayout) {
                    val dayAsString = v.findViewById<TextView>(R.id.tv_btn_calendar_day)
                    val dayAsNumber = v.findViewById<TextView>(R.id.tv_btn_calendar_number)

                    dayAsNumber.text = dayNumber
                    dayAsString.text = dayOfWeek
                    nextDate = calendar.add(Calendar.DATE, 1)
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


    private fun initViews() {
        btnCalendarDay1 = findViewById(R.id.frame_button)
        btnCalendarDay2 = findViewById(R.id.frame_button2)
        btnCalendarDay3 = findViewById(R.id.frame_button3)
        btnCalendarDay4 = findViewById(R.id.frame_button4)
        btnCalendarDay5 = findViewById(R.id.frame_button5)
        btnCalendarDay6 = findViewById(R.id.frame_button6)
        recyclerView = findViewById(R.id.recycler_view_task)
        recyclerView.setHasFixedSize(true)

        calendarDayButton = findViewById(R.id.frame_button)
        layout = findViewById(R.id.button_plan_layout)
        databaseHandler = CustomTaskAdapter(this)

    }

    private fun setNextDayDate() {
        nextDay = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        dayNumber = calendar.get(Calendar.DAY_OF_MONTH).toString()
        dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())
    }

    fun deleteTask(task: Task):Boolean {
        return databaseHandler?.deleteTask(task)!!
    }

    private fun setupTaskListInRecyclerView(day: Int) {

        val databaseHandler: CustomTaskAdapter = CustomTaskAdapter(this)
        val filter = databaseHandler.getPlanDay(day)
        if (filter.size > 0) {
            tv_no_task_calendar.visibility = GONE
            recyclerView.visibility = VISIBLE
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = TaskAdapter(this, filter)
        } else {
            tv_no_task_calendar.visibility = VISIBLE
            recyclerView.visibility = GONE
        }

    }


    private fun setDate(day: Int):Boolean {
        return if (day in 0..6){
            nextDate = calendar.add(Calendar.DATE, day)
            val month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
            val year = calendar.get(Calendar.YEAR)

            setCalendarButtonsUnchecked()
            setupTaskListInRecyclerView(calendar.time.day)
            tv_tittle_monthCalendar.text = "$month, $year"
            nextDate = calendar.add(Calendar.DATE, -day)
            true
        }else false
    }
}