package com.example.haircare.customplan

import android.os.Bundle
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import com.example.haircare.R

class CreateCustomPlan : AppCompatActivity() {

    private lateinit var expandableList: ExpandableListView
    private var listGroup: ArrayList<String> = ArrayList(6)
    var mapChild: MutableMap<Int, ArrayList<String>> = mutableMapOf()
    var plan: CustomPlan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_custom_plan)
        initViews()

    }

    private fun initViews() {
        expandableList = findViewById(R.id.expandable_list)
        listGroup.add("poniedziałek")
        listGroup.add("wtorek")
        listGroup.add("środa")
        listGroup.add("czwartek")
        listGroup.add("piątek")
        listGroup.add("sobota")
        listGroup.add("niedziela")
    }



    private fun takeDay(day: String): Int {
        var i: Int = 0
        if (day == "poniedziałek") i = 0
        if (day == "wtorek") i = 1
        if (day == "środa") i = 2
        if (day == "czwartek") i = 3
        if (day == "piątek") i = 4
        if (day == "sobota") i = 5
        if (day == "niedziela") i = 6
        return i
    }

    private fun addRow(day: Int , task: String){
        plan?.taskList?.get(day)?.add(task)
        // dodaj do planu w miejscu DAY kolejny task ( kolejny element array
    }

    private fun addCustomPlan(){

    }

}
