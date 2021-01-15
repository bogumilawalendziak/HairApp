package com.example.haircare

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.haircare.calendar.MyCalendar
import com.example.haircare.calendar.Task
import com.example.haircare.calendar.Task_DB_Helper
import com.example.haircare.plans.HairCarePlan
import com.example.haircare.plans.Plan
import com.example.haircare.plans.PlanCreate
import com.example.haircare.scanner.Scanner
import com.example.haircare.test.StartTestActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.button_knowledge.view.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var name: String? = null
    var product: String? = null

    val calendar: Date = Calendar.getInstance().time


    private lateinit var btnMainMenu1: FrameLayout
    private lateinit var btnMainMenu2: FrameLayout
    private lateinit var btnKnowledge1: FrameLayout
    private lateinit var btnKnowledge2: FrameLayout
    private lateinit var btnKnowledge3: FrameLayout
    private lateinit var btnKnowledge4: FrameLayout
    lateinit var layout: LinearLayout
    lateinit var sp: SharedPreferences
    lateinit var noPlan: TextView
    lateinit var toggle: ActionBarDrawerToggle
    var context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sp = getSharedPreferences("sh_pref", MODE_PRIVATE)
        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.btn_hair -> startActivity(Intent(this, StartTestActivity::class.java))
                R.id.btn_plans -> startActivity(Intent(this, HairCarePlan::class.java))
                R.id.btn_scanner -> startActivity(Intent(this, Scanner::class.java))
                R.id.btn_calendar -> startActivity(Intent(this, MyCalendar::class.java))
            }
            true
        }

        initViews()
        if (sp.getBoolean("sw_low", true) ||
            sp.getBoolean("sw_med", true) ||
            sp.getBoolean("sw_hig", true)
        ) {

        } else {

        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun buttonInit(button: FrameLayout,tittle: String, description: String) {
        button.tv_knowledge_tittle.text = tittle
        button.tv_knowledge_description.text = description

    }

    companion object {
        private fun takePlanWeek(sp: SharedPreferences): Plan {

            val planList = PlanCreate.getPlan()
            var plan: Plan = planList[1]

            var hairType: String = when {
                sp.getBoolean("sw_low", true) -> "niskoporowate"
                sp.getBoolean("sw_med", true) -> "srednioporowate"
                sp.getBoolean("sw_hig", true) -> "wysokoporowate"
                else -> "no_plan"
            }

            for (item in planList) {
                if (item.hairType == hairType) {
                    plan = item
                }
            }
            return plan

        }

        fun takePlanDay(day: Int, sp: SharedPreferences, context: Context): MutableList<Task> {
            val taskList: MutableList<Task> = mutableListOf()
            var dbhandler = Task_DB_Helper(context)
            var numer: Array<Int>? = takePlanWeek(sp).taskList[day]
            for (num in numer!!) {
                taskList.add(dbhandler.viewTasks(num))
            }
            return taskList
        }
    }

    private fun initViews() {
        btnMainMenu1 = findViewById(R.id.btn_menu_main1)
        btnMainMenu2 = findViewById(R.id.btn_menu_main2)
        btnKnowledge1 = findViewById(R.id.btn_knowledge1)
        btnKnowledge2 = findViewById(R.id.btn_knowledge2)
        btnKnowledge3 = findViewById(R.id.btn_knowledge3)
        btnKnowledge4 = findViewById(R.id.btn_knowledge4)
        noPlan = findViewById(R.id.tv_layout_main_no_plan)
        layout = findViewById(R.id.layout_button_category)
        noPlan.visibility = View.GONE
        layout.visibility = View.VISIBLE
        buttonInit(btnKnowledge1,"Olejowanie","Coś tam coś tam")
        buttonInit(btnKnowledge2,"Henna","Coś tam coś tam")
        buttonInit(btnKnowledge3,"PEH","Coś tam coś tam")
        buttonInit(btnKnowledge4,"Mycie","Coś tam coś tam")

    }
}
