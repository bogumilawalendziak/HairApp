package com.example.haircare.customplan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.haircare.R

class BaseExpandableListViewAdapter(
    var context: Context,
    var header: MutableList<String>,
    var body: MutableMap<Int, MutableList<CustomPlan>>,
) : BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return header.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return body[groupPosition]!!.size
    }

    override fun getGroup(groupPosition: Int): String {
        return header[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): CustomPlan {
        return body[groupPosition]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.list_group, null)
        }


        var tittle = convertView?.findViewById<TextView>(R.id.list_parent)

        tittle!!.text = getGroup(groupPosition).toString()
            return convertView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?,
    ): View? {
        var convertView = convertView
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.list_child, null)
        }


        var tittle = convertView?.findViewById<TextView>(R.id.tv_list_child)
        tittle!!.text = getChild(groupPosition,childPosition).task.toString()

        var tittle2 = convertView?.findViewById<TextView>(R.id.tv_list_child2)
        tittle2!!.text = getChild(groupPosition,childPosition).peh.toString()
        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}
