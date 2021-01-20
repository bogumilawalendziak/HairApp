package com.example.haircare.customplan

data class CustomPlan(
    var taskList: MutableMap<Int, MutableList<String>>,
    var planName: String,
)