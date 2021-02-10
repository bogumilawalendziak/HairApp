package com.example.haircare

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ComplexColorCompat
import androidx.lifecycle.ViewModelProvider
import com.example.haircare.calculations.CalculationOfIngredients
import com.example.haircare.calendar.MyCalendar
import com.example.haircare.calendar.Task
import com.example.haircare.calendar.TaskViewModel
import com.example.haircare.chart.PercValueFormatter
import com.example.haircare.scanner.Scanner
import com.example.haircare.test.StartTestActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.button_knowledge.view.*
import kotlinx.android.synthetic.main.button_main_menu.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    var name: String? = null
    var product: String? = null

    private lateinit var pieChart: PieChart

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
                R.id.btn_scanner -> startActivity(Intent(this, Scanner::class.java))
                R.id.btn_calendar -> startActivity(Intent(this, MyCalendar::class.java))
            }
            true
        }

        initViews()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun buttonInit(button: FrameLayout, tittle: String, description: String) {
        button.tv_knowledge_tittle.text = tittle
        button.tv_knowledge_description.text = description

    }


    private fun initViews() {

        pieChart = findViewById(R.id.pie_chart)
        ingredientsPercentage()
    }

    private fun ingredientsPercentage(){
        val model = ViewModelProvider(this).get(TaskViewModel::class.java)

        val list = model.readAllTask.observe(this,{taskList->
            val data = CalculationOfIngredients().amountIngredients(taskList)
            val pieDataSet= PieDataSet(data,"")
            var colors = intArrayOf(R.color.colorLightBlue,R.color.color2,R.color.color3)
            pieDataSet.setColors(colors,context)
            pieDataSet.valueTextColor = Color.WHITE
            pieDataSet.valueTextSize= 15F


            val pieData= PieData(pieDataSet)
            pieChart.description.isEnabled=false
            pieChart.data=pieData
            pieChart.animate()
            pieChart.setDrawEntryLabels(true)
            pieChart.setEntryLabelColor(Color.WHITE)
            pieChart.setEntryLabelTextSize(11F)
            pieChart.legend.isEnabled=false
            pieChart.setHoleColor(Color.TRANSPARENT)
            pieDataSet.valueFormatter = DefaultValueFormatter(1)
            pieChart.holeRadius = 25f
            pieChart.transparentCircleRadius=35f
            pieChart.setUsePercentValues(true)
            pieDataSet.valueFormatter=PercValueFormatter()

        })
    }
}

