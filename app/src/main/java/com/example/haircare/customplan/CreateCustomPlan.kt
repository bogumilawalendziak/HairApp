package com.example.haircare.customplan

import android.os.Bundle
import android.widget.Button
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import com.example.haircare.R
import kotlinx.android.synthetic.main.activity_create_custom_plan.*

class CreateCustomPlan : AppCompatActivity() {

    private lateinit var expandableList: ExpandableListView
    private lateinit var button: Button
    private var listGroup: MutableList<String> = ArrayList(7)
    var mapChild: MutableMap<Int, MutableList<CustomPlan>> = mutableMapOf()
    var plan: CustomPlan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_custom_plan)
        initViews()

        val day = spinner1.selectedItem.toString()
        println(day)
        val peh = spinner3.selectedItem.toString()
        println(peh)
        val task = tv_put_task.text.toString()
        addRow(takeDay(day), task, peh)

        expandableList.setAdapter(BaseExpandableListViewAdapter(this, listGroup, mapChild))

        button.setOnClickListener {


            val day = spinner1.selectedItem.toString()
            println(day)
            val peh = spinner3.selectedItem.toString()
            println(peh)
            val task = tv_put_task.text.toString()
            addRow(takeDay(day), task, peh)
        }

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
        addRow(3,"xD","LOOOOOL")
        addRow(2,"xD","LOOOOOL")

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

    private fun addRow(day: Int, task: String, peh: String) {
        plan = CustomPlan(peh,task,day)
        var listPlan: MutableList<CustomPlan> = listOf<CustomPlan>(plan!!) as MutableList<CustomPlan>
       // listPlan.add(CustomPlan(peh,task,day))
        mapChild.put(day,listPlan)
        println(mapChild[day])
        // dodaj do planu w miejscu DAY kolejny task ( kolejny element array
    }

    private fun addCustomPlan() {

    }
}
