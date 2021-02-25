package com.example.haircare.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haircare.R
import com.example.haircare.customplan.CreateCustomPlan
import kotlinx.android.synthetic.main.activity_mycalendar.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import android.view.LayoutInflater

import android.view.ViewGroup








class MyCalendar : AppCompatActivity() {

    var name: String? = null
    var product: String? = null

    private val calendar: Calendar = Calendar.getInstance()

    private lateinit var nextDate: Unit
    private lateinit var nextDay: String
    private lateinit var dayOfWeek: String
    private lateinit var dayNumber: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var layout: LinearLayout
    private lateinit var mTaskViewModel: TaskViewModel
    val format = SimpleDateFormat("MM-dd-yyyy")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mycalendar)

        initViews()
        setButtonDateTv()
        buttonInit()
        setCalendarButtonsUnchecked()
        setDate(calendar.time)
        findViewById<FrameLayout>(R.id.frame_button).isEnabled = false
        btnClickListener()

        btn_calendar_create_task.setOnClickListener {
            startActivity(Intent(this, CreateCustomPlan::class.java))
        }

    }


    private fun buttonInit() {

        var days: Int = 0
        for (i in 0 until layout.childCount) {
            val v: View = layout.getChildAt(i)

            if (v is FrameLayout) {
                val dayAsString = v.findViewById<TextView>(R.id.tv_btn_calendar_day)
                val dayAsNumber = v.findViewById<TextView>(R.id.tv_btn_calendar_number)

                dayAsNumber.text = dayNumber
                dayAsString.text = dayOfWeek

                nextDate = calendar.add(Calendar.DATE, 1)
                days += 1
                setButtonDateTv()
            }
        }
        nextDate = calendar.add(Calendar.DATE, -days)

    }

    private fun btnClickListener() {

        for (i in 0 until layout.childCount) {
            val v: View = layout.getChildAt(i)

            if (v is FrameLayout) {
                v.setOnClickListener {
                    setCalendarButtonsUnchecked()
                    v.isEnabled = false

                    calendar.add(Calendar.DATE, i)
                    setDate(calendar.time)
                    calendar.add(Calendar.DATE, -i)

                }
            }
        }
    }

    private fun setCalendarButtonsUnchecked() {

        for (i in 0 until layout.childCount) {
            val v: View = layout.getChildAt(i)

            if (v is FrameLayout) {
                v.isEnabled = true
            }
        }
    }


    private fun initViews() {

        recyclerView = findViewById(R.id.recycler_view_task)
        layout = findViewById(R.id.button_plan_layout)
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

    }

    private fun setButtonDateTv() {
        nextDay = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        dayNumber = calendar.get(Calendar.DAY_OF_MONTH).toString()
        dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())
    }

    fun deleteTask(task: TaskEntity) {
        mTaskViewModel.deleteTask(task)
    }

    private fun setupTaskListInRecyclerView(day: String) {
println("****** szukany dzień : $day")
        mTaskViewModel.getTasksAtDay(day).observe(this, { task ->
            if (task.size > 0) {
                tv_no_task_calendar.visibility = GONE
                recyclerView.visibility = VISIBLE
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = TaskAdapter(this, task)
            } else {
                tv_no_task_calendar.visibility = VISIBLE
                recyclerView.visibility = GONE
            }
        }
        )
    }


    private fun setDate(day: Date) {

        val strDate: String = format.format(day)
        val month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
        val year = calendar.get(Calendar.YEAR)

        setupTaskListInRecyclerView(strDate)
        tv_tittle_monthCalendar.text = "$month, $year"
    }
}