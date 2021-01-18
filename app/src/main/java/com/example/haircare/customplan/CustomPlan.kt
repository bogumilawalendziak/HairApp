package com.example.haircare.customplan

data class CustomPlan(
    var taskList: MutableMap<Int, MutableList<String>>,
    val planName: String,
)