package com.example.haircare.customplan

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.*
import com.example.haircare.R

class Dialog(val context: Context, private val target: ProgressBar, private val length: TextView) {

    fun createDialog(): Dialog {

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_pick_hair, null)
        view.setBackgroundColor(Color.TRANSPARENT)
        val userTarget = view.findViewById<NumberPicker>(R.id.target_number)
        val userActualHairLength = view.findViewById<NumberPicker>(R.id.length_number)
        val builder = AlertDialog.Builder(context)


        builder.setTitle(context.getString(R.string.hair_growing))
            .setPositiveButton("ok") { dialog, which ->
                if (userActualHairLength.value < userTarget.value) {

                    target.progress = (userActualHairLength.value * 100 / userTarget.value).toInt()
                    length.text = "${userActualHairLength.value}cm"
                }
            }
            .setNegativeButton("anuluj") { dialog, which -> dialog.dismiss() }

        builder.setView(view)
        builder.create()

        setNumberPickerValues(userTarget, userActualHairLength)

        return builder.show()
    }

    private fun setNumberPickerValues(
        userTarget: NumberPicker,
        userActualHairLength: NumberPicker,
    ) {
        userTarget.minValue = 1
        userTarget.maxValue = 100
        userActualHairLength.minValue = 0
        userActualHairLength.maxValue = 100
    }

}
