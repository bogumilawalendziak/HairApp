package com.example.haircare

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.haircare.MainActivity.Companion.takePlanWeek
import com.example.haircare.calendar.CareSchedule
import com.example.haircare.calendar.Task
import com.example.haircare.calendar.TaskAdapter
import com.example.haircare.calendar.Task_DB_Helper
import com.example.haircare.hairsettings.HairMenu
import com.example.haircare.plans.HairCarePlan
import com.example.haircare.plans.Plan
import com.example.haircare.plans.PlanCreate
import com.example.haircare.scanner.Scanner
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    var name: String? = null
    var product: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    val dateTime: LocalDateTime = LocalDateTime.now()

    lateinit var listView: ListView
    lateinit var sp: SharedPreferences
    lateinit var toggle: ActionBarDrawerToggle
    var context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context: Context = this
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sp = getSharedPreferences("sh_pref", MODE_PRIVATE)
        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.btn_hair -> startActivity(Intent(this, HairMenu::class.java))
                R.id.btn_plans -> startActivity(Intent(this,HairCarePlan::class.java))
                R.id.btn_scanner -> startActivity(Intent(this, Scanner::class.java))
                R.id.btn_calendar -> startActivity(Intent(this, CareSchedule::class.java))
            }
            true
        }

        initViews()
        takePlan()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

     private fun takePlan(){
        val day = dateTime.dayOfWeek.toString().toLowerCase()
        val taskList = takePlanDay(day,sp,context)

        try {
            listView.adapter = TaskAdapter(this, taskList, R.layout.task_view)

        } catch (e: Exception) {
            listView.visibility = View.GONE

        }
    }
    companion object {
        fun takePlanWeek(sp: SharedPreferences): Plan {

            var hairType: String
            val planList = PlanCreate.getPlan()
            lateinit var plan: Plan

            when {
                sp.getBoolean("sw_low", true) -> hairType = "niskoporowate"
                sp.getBoolean("sw_med", true) -> hairType = "srednioporowate"
                sp.getBoolean("sw_hig", true) -> hairType = "wysokoporowate"
                else -> hairType = "no_plan"
            }
            println("Actual plna: $hairType")

            for (item in planList) {
                if (item.hairType == hairType) {
                    plan = item
                }
            }
            return plan
        }

        fun takePlanDay(day: String, sp: SharedPreferences, context: Context): MutableList<Task> {
            val taskList: MutableList<Task> = mutableListOf()
            var dbhandler = Task_DB_Helper(context)
            var numer: Array<Int>? = takePlanWeek(sp).taskList.get(day)
            for (num in numer!!) {
                taskList.add(dbhandler.viewTasks(num))
            }
            return taskList
        }
    }
    private fun initViews() {

        listView = findViewById(R.id.lv_task)
        listView.visibility = View.VISIBLE
    }
}
