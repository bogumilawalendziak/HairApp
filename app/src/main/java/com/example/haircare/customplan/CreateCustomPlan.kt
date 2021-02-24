package com.example.haircare.customplan

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.haircare.R
import com.example.haircare.calendar.TaskEntity
import com.example.haircare.calendar.TaskViewModel
import kotlinx.android.synthetic.main.activity_create_custom_plan.*
import java.util.*


class CreateCustomPlan : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var btnWashTask: Button
    private lateinit var btnEmolientTask: Button
    private lateinit var btnHumektantTask: Button
    private lateinit var btnHennaTask: Button
    private lateinit var btnLotionTask: Button
    private lateinit var btnProteinTask: Button
    private lateinit var btnLamiTask: Button
    var calendar: Calendar = Calendar.getInstance()
    var strDate: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_custom_plan)

        initViews()
        val mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        button.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            if (createTask(mTaskViewModel)) {
                builder.setTitle("Zapisano zadanie!")
                tv_put_task.text.clear()
                btnClear(layout_week_day_btn)
                btnClear(layout_tasks_btn_bottom)
                btnClear(layout_tasks_btn_top)

            } else {
                builder.setTitle("Uzupełnij wszystkie pola.")
            }
            builder.setPositiveButton("ok", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            }).create().show()
        }

        date_picker.setOnDateChangedListener { view, year, month, day ->
            month+1
            strDate = "$month-$day-$year"
        }

        btnTaskClicked(layout_tasks_btn_top, layout_tasks_btn_bottom)
        btnTaskClicked(layout_tasks_btn_bottom, layout_tasks_btn_top)
        btnWeekClicked(layout_week_day_btn)
    }

    private fun createTask(mTaskViewModel: TaskViewModel): Boolean {
        val day = getSelectedCalendarButton(strDate)
        val peh = getSelectedTaskButton()
        val task = tv_put_task.text.toString()
        println(" dzień : $day , peh : $peh i task : $task")
        if (task.isNotEmpty() && day != null && peh != null) {

            mTaskViewModel.addTask(TaskEntity(task, peh, day))
            return true
        }
        return false
    }

    private fun getSelectedCalendarButton(strDate: String?): String {

        return strDate!!
    }

    private fun getSelectedTaskButton(): String? {

        when {
            !btnWashTask.isEnabled -> {
                return "mycie"
            }
            !btnHennaTask.isEnabled -> {
                return "henna"
            }
            !btnLamiTask.isEnabled -> {
                return "laminowanie"
            }
            !btnEmolientTask.isEnabled -> {
                return "emolienty"
            }
            !btnHumektantTask.isEnabled -> {
                return "humektanty"
            }
            !btnLotionTask.isEnabled -> {
                return "wcierka"
            }
            !btnProteinTask.isEnabled -> {
                return "proteiny"
            }
        }

        return null
    }


    private fun initViews() {
        button = findViewById(R.id.btn_add_custom_task)

        btnWashTask = findViewById(R.id.btn_wash_task)
        btnEmolientTask = findViewById(R.id.btn_emolient_task)
        btnHumektantTask = findViewById(R.id.btn_nawilżanie_task)
        btnHennaTask = findViewById(R.id.btn_henna_task)
        btnLotionTask = findViewById(R.id.btn_wcierka_task)
        btnLamiTask = findViewById(R.id.btn_laminowanie_task)
        btnProteinTask = findViewById(R.id.btn_protein_task)
    }

    /**
     * If input does not belong to days of week in PL language
     * returns i=-1
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


    private fun btnClear(layout: LinearLayout) {
        for (i in 0 until layout.childCount) {
            val v: View = layout.getChildAt(i)
            if (v is Button) {
                v.isEnabled = true
                v.setTextColor(R.color.colorBackground)
            }
        }
    }

    private fun btnTaskClicked(layout1: LinearLayout, layout2: LinearLayout) {
        for (i in 0 until layout1.childCount) {
            val v: View = layout1.getChildAt(i)
            if (v is Button) {
                v.setOnClickListener {
                    btnClear(layout1)
                    btnClear(layout2)
                    v.isEnabled = false
                    v.setTextColor(getColor(R.color.white))
                }
            }
        }
    }

    private fun btnWeekClicked(layout: LinearLayout) {
        for (i in 0 until layout.childCount) {
            val v: View = layout.getChildAt(i)
            if (v is Button) {
                v.setOnClickListener {
                    btnClear(layout)
                    v.isEnabled = false
                    v.setTextColor(getColor(R.color.white))
                }
            }
        }
    }
}

