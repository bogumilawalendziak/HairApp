package com.example.haircare.chart

import android.content.Context
import android.graphics.Color
import com.example.haircare.R
import com.example.haircare.calculations.CalculationOfIngredients
import com.example.haircare.calendar.TaskEntity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.DefaultValueFormatter

class PieChartCustom(private val taskList:MutableList<TaskEntity>, private val pieChart:PieChart, val context: Context) {

    fun createChart(){
        val data = CalculationOfIngredients().amountIngredients(taskList)
        val pieDataSet = PieDataSet(data, "")
        var colors = intArrayOf(R.color.lightblue, R.color.colorBackground, R.color.color3)
        pieDataSet.setColors(colors, context)
        pieDataSet.valueTextColor = Color.WHITE
        pieDataSet.valueTextSize = 15F
        pieDataSet.valueFormatter = DefaultValueFormatter(1)
        pieDataSet.valueFormatter = PercentageValueFormatter()

        val pieData = PieData(pieDataSet)
        pieChart.description.isEnabled = false
        pieChart.data = pieData
        pieChart.animate()
        pieChart.setDrawEntryLabels(true)
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(11F)
        pieChart.legend.isEnabled = false
        pieChart.setHoleColor(Color.TRANSPARENT)
        pieChart.holeRadius = 25f
        pieChart.transparentCircleRadius = 35f
        pieChart.setUsePercentValues(true)

    }
}