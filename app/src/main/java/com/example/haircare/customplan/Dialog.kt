package com.example.haircare.customplan

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.NumberPicker
import android.widget.ProgressBar
import android.widget.TextView
import com.example.haircare.R

class Dialog(val context: Context, val target: ProgressBar, val length: TextView) {

    fun createDialog(): Dialog {


        val view = LayoutInflater.from(context).inflate(R.layout.dialog, null)
        view.setBackgroundColor(Color.TRANSPARENT)
        val userTarget = view.findViewById<NumberPicker>(R.id.target_number)
        val userActualHairLength = view.findViewById<NumberPicker>(R.id.length_number)
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Zapusczanie włosów")
            .setPositiveButton("ok", DialogInterface.OnClickListener { dialog, which ->
                if (userActualHairLength.value < userTarget.value) {
                    val x = userActualHairLength.value
                    val y = userTarget.value
                    val progressValue = (x * 100 / y).toInt()
                    target.progress = progressValue
                    length.text = "${x}cm"
                }
            })
            .setNegativeButton("anuluj", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })

        builder.setView(view)
        builder.create()

        userTarget.minValue = 1
        userTarget.maxValue = 100
        userActualHairLength.minValue = 0
        userActualHairLength.maxValue = 100

        return builder.show()
    }

}
