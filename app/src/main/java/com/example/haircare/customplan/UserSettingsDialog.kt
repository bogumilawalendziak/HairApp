package com.example.haircare.customplan

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.*
import com.example.haircare.R

class UserSettingsDialog(val context: Context, private val length: TextView, private val userName: TextView,
                         private val userHairType: TextView,private val target: ProgressBar) {


    fun createSettingsDialog(): Dialog {

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_settings, null)
        val name = view.findViewById<EditText>(R.id.et_name_settings)
        val hairLength= view.findViewById<EditText>(R.id.et_hair_length_settings)
        val hairType= view.findViewById<Spinner>(R.id.spinner_settings)
        val builder = AlertDialog.Builder(context)


        builder.setTitle(context.getString(R.string.twoje_dane))
            .setPositiveButton("ok") { dialog, which ->
                if (name.text !=null && hairLength.text!=null && hairType!=null) {

                    length.text = "${hairLength.text}cm"
                    userName.text = "${name.text}!"
                    userHairType.text = hairType.selectedItem.toString()
                }
            }
            .setNegativeButton("anuluj") { dialog, which -> dialog.dismiss() }

        builder.setView(view)
        builder.create()


        return builder.show()
    }
}
