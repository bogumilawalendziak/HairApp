package com.example.haircare.test

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.haircare.R
import kotlinx.android.synthetic.main.activity_main_test.*

class StartTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_test)
        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN
        btn_start.setOnClickListener(){

            val intent = Intent(this,QuestionActivity::class.java)
            startActivity(intent)
            finish()


        }
    }
}
