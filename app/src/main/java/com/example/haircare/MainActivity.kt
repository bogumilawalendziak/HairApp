package com.example.haircare

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haircare.calculations.CalculationOfIngredients
import com.example.haircare.calendar.MyCalendar
import com.example.haircare.calendar.TaskViewModel
import com.example.haircare.chart.PercValueFormatter
import com.example.haircare.menucard.MenuCardTaskAdapter
import com.example.haircare.scanner.Scanner
import com.example.haircare.test.StartTestActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import kotlinx.android.synthetic.main.activity_main.*
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
    }

    override fun onStart() {
        super.onStart()
        initViews()
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
        ingredientsPercentage()
        //TODO: do poprawy te dni
        setupTaskListInRecyclerView(Calendar.getInstance().time.day-1)

    }

    private fun ingredientsPercentage() {

        mTaskViewModel.readAllTask.observe(this, { taskList ->
            val data = CalculationOfIngredients().amountIngredients(taskList)
            val pieDataSet = PieDataSet(data, "")
            var colors = intArrayOf(R.color.colorLightBlue, R.color.color2, R.color.color3)
            pieDataSet.setColors(colors, context)
            pieDataSet.valueTextColor = Color.WHITE
            pieDataSet.valueTextSize = 15F

            val pieData = PieData(pieDataSet)
            pieChart.description.isEnabled = false
            pieChart.data = pieData
            pieChart.animate()
            pieChart.setDrawEntryLabels(true)
            pieChart.setEntryLabelColor(Color.WHITE)
            pieChart.setEntryLabelTextSize(11F)
            pieChart.legend.isEnabled = false
            pieChart.setHoleColor(Color.TRANSPARENT)
            pieDataSet.valueFormatter = DefaultValueFormatter(1)
            pieChart.holeRadius = 25f
            pieChart.transparentCircleRadius = 35f
            pieChart.setUsePercentValues(true)
            pieDataSet.valueFormatter = PercValueFormatter()

        })
    }

    private fun setupTaskListInRecyclerView(day: Int) {

        mTaskViewModel.getTasksAtDay(day).observe(this, { task ->
            if (task.size > 0) {
                recyclerView.visibility = View.VISIBLE
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = MenuCardTaskAdapter(this, task)
            }
        }
        )
    }
}

