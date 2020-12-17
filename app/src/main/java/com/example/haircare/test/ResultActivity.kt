package com.example.haircare.test

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.haircare.MainActivity
import com.example.haircare.R
import kotlinx.android.synthetic.main.activity_test_result.*
import java.util.*

//Checking test result
class ResultActivity : AppCompatActivity() {
    var choose: ArrayList<Int>? = ArrayList<Int>()
    var test_result: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_result)

        choose = intent.getIntegerArrayListExtra(CreatingQuestion.HAIR_TYPE)
        var hair: Map<String, Int> = mapOf(
            "porowate" to choose!!.count { it == 1 },
            "średnioporowate" to choose!!.count { it == 2 },
            "niskoporowate" to choose!!.count { it == 3 },
            "kręcone" to choose!!.count { it == 4 })
        var maxValue = hair.maxBy { it.value }
        test_result = maxValue!!.key
        tv_result.text = maxValue!!.key

        btn_return.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_again.setOnClickListener() {
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}
