package com.example.haircare.plans

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import com.example.haircare.R

class HairCarePlan : AppCompatActivity() {
    lateinit var checkboxLow: AppCompatCheckBox
    lateinit var checkboxMed: AppCompatCheckBox
    lateinit var checkboxHigh: AppCompatCheckBox

    private val SH_PREF: String = "sh_pref"
    private val SW_LOW: String = "sw_low"
    private val SW_MED: String = "sw_med"
    private val SW_HIG: String = "sw_hig"

    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hair_care_plan)
        sp = getSharedPreferences(SH_PREF, MODE_PRIVATE)
        checkboxLow = findViewById(R.id.cz_niskoporowate)
        checkboxMed = findViewById(R.id.cz_wysokoporowate)
        checkboxHigh = findViewById(R.id.cz_srednioporowate)

        checkboxLow.setOnClickListener {
        }
        checkboxMed.setOnClickListener {
        }
        checkboxHigh.setOnClickListener {
        }

        checkboxLow.setOnCheckedChangeListener { buttonView, isChecked ->
            save(SW_LOW,isChecked)
        }

        checkboxMed.setOnCheckedChangeListener { buttonView, isChecked ->
            print("MED ")
            save(SW_MED,isChecked)
        }
        checkboxHigh.setOnCheckedChangeListener { buttonView, isChecked ->

            save(SW_HIG,isChecked)
        }
    }

    private fun save(value: String, key:Boolean) {

        val editor: SharedPreferences.Editor = sp.edit()
        editor.putBoolean(value, key)
        println(" set $value as $key")
        editor.apply()

    }


}
