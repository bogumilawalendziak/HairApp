package com.example.haircare.scanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import com.example.haircare.R

class MyAdapter(context: Context, data: MutableList<Ingredients>, var resources: Int) : BaseAdapter() {

    private val myContext: Context = context
    private val myData: MutableList<Ingredients> = data


    override fun getCount(): Int {
        return myData.size
    }

    override fun getItem(position: Int): Any {

        return myData[position]
    }

    override fun getItemId(position: Int): Long {

        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(myContext)
        val view: View = layoutInflater.inflate(resources, null)

        val ilName: TextView = view.findViewById(R.id.il_name)
        val ilDescription: TextView = view.findViewById(R.id.il_description)
        val ilPeh: TextView = view.findViewById(R.id.il_PEH)

        ilName.text = myData[position].name
        ilDescription.text = myData[position].description
        if(myData[position].peh=="n"){
            ilPeh.text = "neutralna"
        } else if(myData[position].peh=="P"){
            ilPeh.text = "Proteiny"
            ilPeh.setTextColor(R.color.lightblue2)
        }else if(myData[position].peh=="H"){
            ilPeh.text = "Humektanty"
            ilPeh.setTextColor(R.color.color3)
        }else if(myData[position].peh=="E"){
            ilPeh.text = "Emolienty"
            ilPeh.setTextColor(R.color.colorBackground)
        }

        return view
    }

}