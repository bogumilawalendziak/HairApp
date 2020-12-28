package com.example.haircare.plans

data class Plan(
    var taskList: Map<String, Array<Int>>,
    val hairType: String,
                )