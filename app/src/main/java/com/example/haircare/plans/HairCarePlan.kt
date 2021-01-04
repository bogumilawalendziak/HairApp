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
        checkboxLow = findViewById(R.id.checkbox_low_porosity)
        checkboxHigh = findViewById(R.id.checkbox_high_porosity)
        checkboxMed = findViewById(R.id.checkbox_medium_porosity)

        checkboxLow.setOnClickListener {
            //checkboxMed.isSelected=false
            //checkboxHigh.isSelected=false
            checkboxMed.isChecked=false
            checkboxHigh.isChecked=false
           // checkboxLow.isSelected=true
            checkboxLow.isChecked=true

        }
        checkboxMed.setOnClickListener {
            //checkboxLow.isSelected=false
            //checkboxHigh.isSelected=false
            checkboxLow.isChecked=false
            checkboxHigh.isChecked=false
            //checkboxMed.isSelected=true
            checkboxMed.isChecked=true
        }
        checkboxHigh.setOnClickListener {
           // checkboxLow.isSelected=false
            //checkboxMed.isSelected=false
            checkboxLow.isChecked=false
            checkboxMed.isChecked=false
            //checkboxHigh.isSelected=true
            checkboxHigh.isChecked=true
        }

        checkboxLow.setOnCheckedChangeListener { buttonView, isChecked ->
            //checkboxLow.isChecked = !checkboxLow.isSelected;

            save(SW_LOW,isChecked)

        }

        checkboxMed.setOnCheckedChangeListener { buttonView, isChecked ->
            //checkboxMed.isChecked = !checkboxMed.isSelected;
            save(SW_MED,isChecked)
        }
        checkboxHigh.setOnCheckedChangeListener { buttonView, isChecked ->
            //checkboxHigh.isChecked = !checkboxHigh.isSelected;
            save(SW_HIG,isChecked)
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
   }


}
