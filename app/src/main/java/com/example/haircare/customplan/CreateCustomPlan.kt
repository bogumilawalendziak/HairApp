package com.example.haircare.customplan

import android.app.AlertDialog
import android.content.DialogInterface
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
    private lateinit var btnMonday: Button
    private lateinit var btnWednesday: Button
    private lateinit var btnTuesday: Button
    private lateinit var btnThursday: Button
    private lateinit var btnFriday: Button
    private lateinit var btnSaturday: Button
    private lateinit var btnSunday: Button
    private lateinit var btnWashTask: Button
    private lateinit var btnEmolientTask: Button
    private lateinit var btnHumektantTask: Button
    private lateinit var btnHennaTask: Button
    private lateinit var btnLotionTask: Button
    private lateinit var btnProteinTask: Button
    private lateinit var btnLamiTask: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_custom_plan)
        initViews()
        val mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
// TEST
        button.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            if (createTask(mTaskViewModel)) {
                builder.setTitle("Zapisano zadanie!")
                tv_put_task.text.clear()
                btnCalendarClear()
                btnTaskClear()

            } else {
                builder.setTitle("Uzupełnij wszystkie pola.")
            }
            builder.setPositiveButton("ok", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            }).create().show()
        }

        btnHumektantTask.setOnClickListener {
            btnTaskClear()
            btnHumektantTask.isEnabled = false
        }
        btnWashTask.setOnClickListener {
            btnTaskClear()
            btnWashTask.isEnabled = false
        }
        btnLotionTask.setOnClickListener {
            btnTaskClear()
            btnLotionTask.isEnabled = false
        }
        btnProteinTask.setOnClickListener {
            btnTaskClear()
            btnProteinTask.isEnabled = false
        }
        btnEmolientTask.setOnClickListener {
            btnTaskClear()
            btnEmolientTask.isEnabled = false
        }
        btnLamiTask.setOnClickListener {
            btnTaskClear()
            btnLamiTask.isEnabled = false
        }
        btnHennaTask.setOnClickListener {
            btnTaskClear()
            btnHennaTask.isEnabled = false
        }

        btnMonday.setOnClickListener {
            btnCalendarClear()
            btnMonday.isEnabled = false
        }

        btnTuesday.setOnClickListener {
            btnCalendarClear()
            btnTuesday.isEnabled = false
        }
        btnWednesday.setOnClickListener {
            btnCalendarClear()
            btnWednesday.isEnabled = false
        }
        btnThursday.setOnClickListener {
            btnCalendarClear()
            btnThursday.isEnabled = false
        }
        btnFriday.setOnClickListener {
            btnCalendarClear()
            btnFriday.isEnabled = false
        }
        btnSaturday.setOnClickListener {
            btnCalendarClear()
            btnSaturday.isEnabled = false
        }
        btnSunday.setOnClickListener {
            btnCalendarClear()
            btnSunday.isEnabled = false
        }


    }

    private fun createTask(mTaskViewModel: TaskViewModel): Boolean {
        val day = getSelectedCalendarButton()
        val peh = getSelectedTaskButton()
        val task = tv_put_task.text.toString()
        // add task to db
        if (task.isNotEmpty() && day != 0 && peh != "null") {

            mTaskViewModel.addTask(TaskEntity(task, peh, day))
            return true
        }
        return false
    }

    private fun getSelectedCalendarButton(): Int {

        when {
            !btnMonday.isEnabled -> {
                return 1
            }
            !btnTuesday.isEnabled -> {
                return 2
            }
            !btnWednesday.isEnabled -> {
                return 3
            }
            !btnThursday.isEnabled -> {
                return 4
            }
            !btnFriday.isEnabled -> {
                return 5
            }
            !btnSaturday.isEnabled -> {
                return 6
            }
            !btnSunday.isEnabled -> {
                return 7
            }
        }
        return 0

    }

    private fun getSelectedTaskButton(): String {

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

        return "null"
    }

    private fun btnCalendarClear() {
        btnMonday.isEnabled = true
        btnTuesday.isEnabled = true
        btnWednesday.isEnabled = true
        btnThursday.isEnabled = true
        btnFriday.isEnabled = true
        btnSaturday.isEnabled = true
        btnSunday.isEnabled = true
    }

    private fun initViews() {
        button = findViewById(R.id.btn_add_custom_task)
        btnMonday = findViewById(R.id.btn_monday_task)
        btnWednesday = findViewById(R.id.btn_wednesday_task)
        btnTuesday = findViewById(R.id.btn_tuesday_task)
        btnThursday = findViewById(R.id.btn_thursday_task)
        btnFriday = findViewById(R.id.btn_friday_task)
        btnSaturday = findViewById(R.id.btn_saturday_task)
        btnSunday = findViewById(R.id.btn_sunday_task)
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
     * returns i=0
     * else return day od week as number
     */
    fun takeDay(day: String): Int {
        var i: Int = -1
        if (day == "poniedziałek") i = 1
        if (day == "wtorek") i = 2
        if (day == "środa") i = 3
        if (day == "czwartek") i = 4
        if (day == "piątek") i = 5
        if (day == "sobota") i = 6
        if (day == "niedziela") i = 7
        return i
    }


    private fun btnTaskClear() {
        btnProteinTask.isEnabled = true
        btnWashTask.isEnabled = true
        btnEmolientTask.isEnabled = true
        btnLamiTask.isEnabled = true
        btnLotionTask.isEnabled = true
        btnHennaTask.isEnabled = true
        btnHumektantTask.isEnabled = true
    }
}
