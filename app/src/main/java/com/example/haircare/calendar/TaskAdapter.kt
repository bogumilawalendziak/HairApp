package com.example.haircare.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.haircare.R

class TaskAdapter(val context: Context, private val list: MutableList<Task>, private val resources: Int): BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //take an XML view, create its object set values, repeat
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view:View = layoutInflater.inflate(resources,null)
        val name: TextView = view.findViewById(R.id.tv_task_name)
        val product: TextView  = view.findViewById(R.id.tv_task_product)
        name.text=list[position].peh
        product.text=list[position].task
        return view
    }

}