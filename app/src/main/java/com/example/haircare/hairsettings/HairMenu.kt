package com.example.haircare.hairsettings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.haircare.R
import com.example.haircare.test.StartTestActivity
import kotlinx.android.synthetic.main.activity_myhair.*

class HairMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myhair)

        btn_test.setOnClickListener() {
            val intent = Intent(this, StartTestActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_myHair.setOnClickListener() {
            val intent = Intent(this, PersonalSettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}