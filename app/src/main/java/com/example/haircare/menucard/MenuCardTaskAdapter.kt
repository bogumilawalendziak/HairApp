package com.example.haircare.menucard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.haircare.MainActivity
import com.example.haircare.R
import com.example.haircare.calendar.TaskEntity
import kotlinx.android.synthetic.main.menu_card_task_view.view.*
import kotlinx.android.synthetic.main.task_view.view.*

class MenuCardTaskAdapter(val context: Context, private val list: MutableList<TaskEntity>) :
    RecyclerView.Adapter<MenuCardTaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuCardTaskAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.menu_card_task_view, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MenuCardTaskAdapter.ViewHolder, position: Int) {
        val item = list.get(position)
        holder.taskName.text = item.task
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskName = view.tv_card_menu_task_name
    }
}
