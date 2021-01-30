package com.example.haircare.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.recyclerview.widget.RecyclerView
import com.example.haircare.R
import kotlinx.android.synthetic.main.task_view.view.*

class TaskAdapter(val context: Context, private val list: MutableList<Task>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.task_view, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskAdapter.ViewHolder, position: Int) {
        val item = list.get(position)
        holder.taskName.text = item.task
        holder.product.text = item.task
        holder.btnDelete.setOnClickListener {view ->
            if (context is MyCalendar) {
                context.deleteTask(item)
            }
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //holds tv and btn
        val btnDelete = view.btn_delete_task
        val taskName = view.tv_task_name
        val product = view.tv_task_product
    }
}
