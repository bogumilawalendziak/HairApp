package com.example.haircare.chart

import com.github.mikephil.charting.formatter.ValueFormatter

class PercentageValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return "${value.toInt()}%"
    }
}