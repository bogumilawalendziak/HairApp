package com.example.haircare.customplan

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.haircare.R
import com.example.haircare.calendar.TaskEntity
import com.example.haircare.calendar.TaskViewModel
import kotlinx.android.synthetic.main.activity_create_custom_plan.*


class CreateCustomPlan : AppCompatActivity() {
    private lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_custom_plan)
        initViews()
        val mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

// TEST
        button.setOnClickListener {
            createTask(mTaskViewModel)
            //show task in list
          // mTaskViewModel.getTasksAtDay(takeDay(day))
        }


    }

    private fun createTask(mTaskViewModel: TaskViewModel) {
        val day = spinner1.selectedItem.toString()
        val peh = spinner3.selectedItem.toString()
        val task = tv_put_task.text.toString()
        // add task to db
        if (task.isNotEmpty()) {
            mTaskViewModel.addTask(TaskEntity(task, peh, takeDay(day)))
        }
    }

    private fun initViews() {
        button = findViewById(R.id.btn_add_custom_task)
    }

    /**
     * If input does not belong to days of week in PL language
     * returns i=0
     * else return day od week as number
     */
    fun takeDay(day: String): Int {
        var i: Int = -1
        if (day == "poniedziałek") i = 0
        if (day == "wtorek") i = 1
        if (day == "środa") i = 2
        if (day == "czwartek") i = 3
        if (day == "piątek") i = 4
        if (day == "sobota") i = 5
        if (day == "niedziela") i = 6
        return i
    }
}
