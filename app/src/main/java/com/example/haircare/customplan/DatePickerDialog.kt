package com.example.haircare.customplan

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.*
import com.example.haircare.R
import kotlinx.android.synthetic.main.dialog_pick_date.view.*
import java.util.*

class DatePickerDialog(val context: Context, private val date: Date , private val tv: TextView) {


    fun createDatePickerDialog(): Dialog{
        var pickedDate: String
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_pick_date, null)

        val builder = AlertDialog.Builder(context)
        view.date_picker.setOnDateChangedListener { view, year, month, day ->
            val dayStr: String
            val monthStr: String
            if(day<10){ dayStr ="0$day"}
            else dayStr="$day"
            if(month<10){ monthStr ="0${month+1}"}
            else monthStr="${month+1}"
            pickedDate = "$monthStr-$dayStr-$year"
            tv.text = pickedDate
        }

        builder.setTitle(context.getString(R.string.wybierz_date))
            .setPositiveButton("ok") { dialog, which ->
            }
            .setNegativeButton("anuluj") { dialog, which -> dialog.dismiss() }
        builder.setView(view)
        builder.create()
        return builder.show()
    }
}
