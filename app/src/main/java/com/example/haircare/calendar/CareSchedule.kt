package com.example.haircare.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.haircare.R
import com.example.haircare.plans.PlanCreate.getPlan
import com.example.haircare.scanner.DB_Helper

class CareSchedule : AppCompatActivity() {

    var dbhandler: Task_DB_Helper? = null
    var name: String? = null
    var product: String? = null

    var numer : Array<Int>? = null
    var taskList: MutableList<Task> = mutableListOf()
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care_schedule)
        listView = findViewById(R.id.lv_task)
        dbhandler = Task_DB_Helper(this)
        takePlan()

    }


  private fun takePlan(){
      val day = "monday"
      val hairType = "wysokoporowate"
      val planList = getPlan()
      for (item in planList) {
          if(item.hairType == hairType)
            {
                numer= item.taskList.get(day)
                 for(num in numer!!){

                     taskList.add(dbhandler!!.viewTasks(num))

                 }
             }
      }
      listView.adapter = TaskAdapter(this, taskList, R.layout.task_view)
  }
}
