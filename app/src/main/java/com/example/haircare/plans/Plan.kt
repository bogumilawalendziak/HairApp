package com.example.haircare.plans

data class Plan(
    var taskList: Map<Int, Array<Int>>,
    val hairType: String,
                )