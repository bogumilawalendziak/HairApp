package com.example.haircare.scanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.haircare.R

class MyAdapter(context: Context, data: MutableList<Ingredients>, var resources:Int) : BaseAdapter() {

    private val myContext: Context = context
    private val mydata: MutableList<Ingredients> = data


    override fun getCount(): Int {
        return mydata.size
    }

    override fun getItem(position: Int): Any {

        return mydata[position]
    }

    override fun getItemId(position: Int): Long {

        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(myContext)
        val view: View = layoutInflater.inflate(resources,null)

        val ilName: TextView = view.findViewById(R.id.il_name)
        val ilDescription: TextView = view.findViewById(R.id.il_description)
        val il_PEH: TextView = view.findViewById(R.id.il_PEH)

        ilName.text = mydata[position].name
        ilDescription.text = mydata[position].description

        return view
    }

}