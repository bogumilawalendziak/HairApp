package com.example.haircare.customplan

import android.os.Bundle
import android.widget.Button
import android.widget.ExpandableListView
import android.widget.ExpandableListView.OnGroupExpandListener
import androidx.appcompat.app.AppCompatActivity
import com.example.haircare.R
import com.example.haircare.calendar.CustomTaskAdapter
import kotlinx.android.synthetic.main.activity_create_custom_plan.*


class CreateCustomPlan : AppCompatActivity() {

    private lateinit var expandableList: ExpandableListView
    private lateinit var button: Button
    private var listGroup: MutableList<String> = ArrayList(7)
    var mapChild: MutableMap<Int, MutableList<CustomPlan>> = mutableMapOf()
    var plan: CustomPlan? = null
    var listPlan: MutableList<CustomPlan> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_custom_plan)
        initViews()
        val databaseHandler: CustomTaskAdapter = CustomTaskAdapter(this)
        expandableList.setAdapter(BaseExpandableListViewAdapter(this, listGroup, mapChild, expandableList))

        button.setOnClickListener {

            val day = spinner1.selectedItem.toString()
            val peh = spinner3.selectedItem.toString()
            val task = tv_put_task.text.toString()
            // add task to db
            if (!task.isEmpty()){
                val lol = databaseHandler.addCustomPlanTask(task, peh, takeDay(day))
            }
            //show task in list
            val filter= databaseHandler.getPlanDay(takeDay(day))
            mapChild.put(takeDay(day), filter)




        }
//////!!!!!!!/////
        expandableList.setOnGroupExpandListener(object : OnGroupExpandListener {
            var previousGroup = -1
            override fun onGroupExpand(groupPosition: Int) {
                if (groupPosition != previousGroup) expandableList.collapseGroup(previousGroup)
                previousGroup = groupPosition
            }
        })

    }

    private fun initViews() {
        expandableList = findViewById(R.id.expandable_list)
        button = findViewById(R.id.btn_add_custom_task)
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

   /* private fun addRow(day: Int, task: String, peh: String) {
       val filter= databaseHandler.getPlanDay(takeDay(day))
       // val filter = listPlan.filter { customPlan -> customPlan.day == day }
        mapChild.put(day, filter as MutableList<CustomPlan>)

    }*/

}
