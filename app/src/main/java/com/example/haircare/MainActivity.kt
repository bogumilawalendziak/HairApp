package com.example.haircare

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haircare.calendar.MyCalendar
import com.example.haircare.calendar.TaskEntity
import com.example.haircare.calendar.TaskViewModel
import com.example.haircare.chart.PieChartCustom
import com.example.haircare.customplan.Dialog
import com.example.haircare.customplan.UserSettingsDialog
import com.example.haircare.menucard.MenuCardTaskAdapter
import com.example.haircare.scanner.Scanner
import com.example.haircare.test.StartTestActivity
import com.github.mikephil.charting.charts.PieChart
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {

    var name: String? = null
    var product: String? = null

    private lateinit var pieChart: PieChart

    lateinit var sp: SharedPreferences
    lateinit var noPlan: TextView
    lateinit var toggle: ActionBarDrawerToggle
    var context: Context = this
    private lateinit var recyclerView: RecyclerView
    private lateinit var mTaskViewModel: TaskViewModel
    private lateinit var buttonSettings: ImageView

    private lateinit var progressCard: androidx.cardview.widget.CardView
    private lateinit var taskCard: androidx.cardview.widget.CardView
    val calendar: Calendar = Calendar.getInstance()
    val format = SimpleDateFormat("MM-dd-yyyy")
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
                R.id.btn_scanner -> startActivity(Intent(this, Scanner::class.java))
                R.id.btn_calendar -> startActivity(Intent(this, MyCalendar::class.java))
            }
            true
        }


        initViews()
        getIngredientsPercentageChart()
        setupTaskListInRecyclerView(calendar.time)

        progressCard.setOnClickListener {
            val target = findViewById<ProgressBar>(R.id.progress_bar_circle)
            val length = findViewById<TextView>(R.id.tv_length)
            Dialog(this, target, length).createDialog()
        }

        buttonSettings.setOnClickListener {
            val target = findViewById<ProgressBar>(R.id.progress_bar_circle)
            val length = findViewById<TextView>(R.id.tv_length)
            val userName = findViewById<TextView>(R.id.tv_name_user)
            val hairType = findViewById<TextView>(R.id.tv_hair_type)
            UserSettingsDialog(this, length, userName, hairType, target).createSettingsDialog()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recycler_view_menu_card)
        recyclerView.setHasFixedSize(true)
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        pieChart = findViewById(R.id.pie_chart)
        progressCard = findViewById(R.id.progress_menu_card)
        buttonSettings = findViewById(R.id.btn_settings)

    }

    private fun getIngredientsPercentageChart() {
        var filter: MutableList<TaskEntity> = mutableListOf()
        mTaskViewModel.readAllTask.observe(this, { task ->
            filter = task.filter {
                checkDate(it.date)
            } as MutableList<TaskEntity>
            PieChartCustom(filter, pieChart, this).createChart()
        })
    }

    private fun checkDate(date: String): Boolean {
        val date = format.parse(date)
       val nowStr = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(Date())
        val now = format.parse(nowStr)
        calendar.add(Calendar.DATE, -30)

        val thirtyDaysBackStr =format.format(calendar.time)
        val thirtyDaysBack = format.parse(thirtyDaysBackStr)
        println("******** $date i $thirtyDaysBack oraz $now *****")
        calendar.add(Calendar.DATE, 30)
        return date in thirtyDaysBack..now
    }

    private fun setupTaskListInRecyclerView(day: Date) {

        var strDay = format.format(day)
        mTaskViewModel.getTasksAtDay(strDay).observe(this, { task ->
            if (task.size > 0) {
                recyclerView.visibility = View.VISIBLE
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = MenuCardTaskAdapter(this, task)
            }
        }
        )
    }
}