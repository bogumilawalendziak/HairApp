package com.example.haircare.calendar

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.haircare.MainActivity.Companion.takePlanDay
import com.example.haircare.MainActivity.Companion.takePlanWeek
import com.example.haircare.R
import java.time.LocalDateTime

class CareSchedule : AppCompatActivity() {

    var dbhandler: Task_DB_Helper? = null
    var name: String? = null
    var product: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    val dateTime: LocalDateTime = LocalDateTime.now()

    private lateinit var listView: ListView
    private lateinit var planTittle: TextView
    private lateinit var sp: SharedPreferences
    private lateinit var tvNoPlan: TextView
    private lateinit var cardPlan: androidx.cardview.widget.CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care_schedule)
        sp = getSharedPreferences("sh_pref", MODE_PRIVATE)

            initViews()
        takePlan()
    }

    private fun takePlan() {
        val day = dateTime.dayOfWeek.toString().toLowerCase() // today
        println("Today: $day")
        val layout: LinearLayout = findViewById(R.id.plan_layout)

        try {
            for (i in 0 until layout.childCount) {
                val v: View = layout.getChildAt(i)
                if (v is LinearLayout) {
                    for (j in 0 until v.childCount) {
                        val l: View = v.getChildAt(j)
                        //get 7 next days -> add 1 for every next listView

                        val dayOfMonth: Int = dateTime.dayOfMonth
                        val nextDayOfMonth = dateTime.withDayOfMonth(dayOfMonth + i).dayOfMonth
                       // val dayValue = dateTime.dayOfWeek.plus(i.toLong() - 1).toString().toLowerCase()
                        val dayValue= dateTime.withDayOfMonth(dayOfMonth + i).dayOfWeek.toString().toLowerCase()
                        if (l is ListView) {
                            println(dayValue)
                            val taskList = takePlanDay(dayValue, sp, context = this)
                            l.adapter = TaskAdapter(this, taskList, R.layout.task_view)

                            val date = v.findViewById<TextView>(R.id.tv_dayAndMonth)
                            val dayAsString = v.findViewById<TextView>(R.id.tv_dayOfWeek)
                            date.text = nextDayOfMonth.toString() +" "+ dateTime.month.toString()
                            dayAsString.text = dayValue
                        }
                    }
                }
            }
        } catch (e: Exception) {
            cardPlan.visibility = View.GONE
            tvNoPlan.visibility = VISIBLE

        }
    }


    private fun initViews() {
        listView = findViewById(R.id.lv_task)
        planTittle = findViewById(R.id.tv_plan_tittle)
        tvNoPlan = findViewById(R.id.tv_no_plan)
        cardPlan = findViewById(R.id.card_plan)
        dbhandler = Task_DB_Helper(this)
        tvNoPlan.visibility = View.GONE
        cardPlan.visibility = VISIBLE
        planTittle.text =" Plan na włosy " + takePlanWeek(sp).hairType
    }
}
