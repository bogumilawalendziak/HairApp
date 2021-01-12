package com.example.haircare.calendar

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.haircare.MainActivity.Companion.takePlanDay
import com.example.haircare.MainActivity.Companion.takePlanWeek
import com.example.haircare.R
import java.text.DateFormat
import java.util.*

class CareSchedule : AppCompatActivity() {

    var dbhandler: Task_DB_Helper? = null
    var name: String? = null
    var product: String? = null

    val calendar: Calendar = Calendar.getInstance()
    val dateNow = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time).toLowerCase()


    private lateinit var listView: ListView
    private lateinit var planTittle: TextView
    private lateinit var sp: SharedPreferences
    private lateinit var tvNoPlan: TextView
    private lateinit var layout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care_schedule)
        sp = getSharedPreferences("sh_pref", MODE_PRIVATE)

        initViews()
        if (sp.getBoolean("sw_low", true) ||
            sp.getBoolean("sw_med", true) ||
            sp.getBoolean("sw_hig", true)
        ) {
            takePlan()
        } else {
            tvNoPlan.visibility = VISIBLE
            layout.visibility = View.GONE
            planTittle.visibility = View.GONE
        }
    }

    private fun takePlan() {
        val date = dateNow.split(",")
        val dayOfWeekAsString = date[0]
        val monthAsString = date[1]
        println("Today: $dayOfWeekAsString") // działa
        val layout: LinearLayout = findViewById(R.id.plan_layout)

        try {
            var addNextDay = 0
            //find LinearLayout
            for (i in 0 until layout.childCount) {
                val v: View = layout.getChildAt(i)
                if (v is LinearLayout) {

                    for (j in 0 until v.childCount) {
                        val l: View = v.getChildAt(j)


                        //find ListView
                        if (l is ListView) {
                            //get 7 next days -> add 1 for every next listView
                            val nextDate = calendar.add(Calendar.DATE, addNextDay)
                            val nextDay = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
                            val dayOfWeekAsInt = calendar.time.day

                            println(dayOfWeekAsInt)
                            val nextDateAsArray = nextDay.split(",")
                            println(dayOfWeekAsInt)
                            val taskList = takePlanDay(dayOfWeekAsInt, sp, context = this)
                            l.adapter = TaskAdapter(this, taskList, R.layout.task_view)

                            val date = v.findViewById<TextView>(R.id.tv_dayAndMonth)
                            val dayAsString = v.findViewById<TextView>(R.id.tv_dayOfWeek)
                            date.text = nextDateAsArray[1]
                            dayAsString.text = nextDateAsArray[0]
                            addNextDay = 1
                        }
                    }
                }
            }
        } catch (e: Exception) {

            tvNoPlan.visibility = VISIBLE

        }
    }


    private fun initViews() {
        listView = findViewById(R.id.lv_task)
        planTittle = findViewById(R.id.tv_plan_tittle)
        tvNoPlan = findViewById(R.id.tv_no_plan)
        layout = findViewById(R.id.layout_calendar_plan)
        dbhandler = Task_DB_Helper(this)
        tvNoPlan.visibility = View.GONE
        layout.visibility = VISIBLE
        planTittle.text = " Plan na włosy " + takePlanWeek(sp).hairType
    }
}