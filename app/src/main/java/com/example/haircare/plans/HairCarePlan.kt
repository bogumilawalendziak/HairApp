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
    lateinit var checkboxCustomPlan: AppCompatCheckBox

    private val SH_PREF: String = "sh_pref"
    private val SW_LOW: String = "sw_low"
    private val SW_MED: String = "sw_med"
    private val SW_HIG: String = "sw_hig"
    private val SW_CUSTOM: String = "sw_custom"

    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hair_care_plan)
        sp = getSharedPreferences(SH_PREF, MODE_PRIVATE)
        checkboxLow = findViewById(R.id.checkbox_low_porosity)
        checkboxHigh = findViewById(R.id.checkbox_high_porosity)
        checkboxMed = findViewById(R.id.checkbox_medium_porosity)
        checkboxCustomPlan = findViewById(R.id.checkbox_custom_plan)

        checkboxLow.setOnClickListener {
            checkboxMed.isChecked=false
            checkboxHigh.isChecked=false
            checkboxLow.isChecked=true
            checkboxCustomPlan.isChecked=false

        }
        checkboxCustomPlan.setOnClickListener {
            checkboxMed.isChecked=false
            checkboxHigh.isChecked=false
            checkboxLow.isChecked=false
            checkboxCustomPlan.isChecked=true

        }
        checkboxMed.setOnClickListener {
            checkboxLow.isChecked=false
            checkboxHigh.isChecked=false
            checkboxMed.isChecked=true
            checkboxCustomPlan.isChecked=false
        }
        checkboxHigh.setOnClickListener {
            checkboxLow.isChecked=false
            checkboxMed.isChecked=false
            checkboxHigh.isChecked=true
            checkboxCustomPlan.isChecked=false
        }

        checkboxLow.setOnCheckedChangeListener { buttonView, isChecked ->
            save(SW_LOW,isChecked)
        }

        checkboxMed.setOnCheckedChangeListener { buttonView, isChecked ->
            save(SW_MED,isChecked)
        }
        checkboxHigh.setOnCheckedChangeListener { buttonView, isChecked ->
            save(SW_HIG,isChecked)
        }
        checkboxCustomPlan.setOnCheckedChangeListener { buttonView, isChecked ->
            save(SW_CUSTOM,isChecked)
        }

        loadData()

    }

    private fun save(value: String, key:Boolean) {

        val editor: SharedPreferences.Editor = sp.edit()
        editor.putBoolean(value, key)
        println(" set $value as $key")
        editor.apply()
    }
   private fun loadData(){
       checkboxLow.isChecked = sp.getBoolean(SW_LOW,false)
       checkboxMed.isChecked = sp.getBoolean(SW_MED,false)
       checkboxHigh.isChecked = sp.getBoolean(SW_HIG,false)
       checkboxCustomPlan.isChecked = sp.getBoolean(SW_CUSTOM,false)
   }


}
