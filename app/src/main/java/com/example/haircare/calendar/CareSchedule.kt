package com.example.haircare.calendar

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.haircare.R
import com.example.haircare.plans.PlanCreate.getPlan

class CareSchedule : AppCompatActivity() {

    var dbhandler: Task_DB_Helper? = null
    var name: String? = null
    var product: String? = null

    var numer: Array<Int>? = null
    var taskList: MutableList<Task> = mutableListOf()
    lateinit var listView: ListView

    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care_schedule)
        listView = findViewById(R.id.lv_task)
        dbhandler = Task_DB_Helper(this)
        takePlan()

    }


    private fun takePlan() {
       sp = getSharedPreferences("sh_pref", MODE_PRIVATE)

        var hairType = "wysokoporowate"
        when {
            sp.getBoolean("sw_low", true) -> hairType = "niskoporowate"
            sp.getBoolean("sw_med", true) -> hairType = "srednioporowate"
            sp.getBoolean("sw_hig", true) -> hairType = "wysokoporowate"
        }
        println(hairType)
        val day = "monday"
        val planList = getPlan()
        for (item in planList) {
            if (item.hairType == hairType) {
                numer = item.taskList.get(day)
                for (num in numer!!) {

                    taskList.add(dbhandler!!.viewTasks(num))

                }
            }
        }
        listView.adapter = TaskAdapter(this, taskList, R.layout.task_view)
    }
}
