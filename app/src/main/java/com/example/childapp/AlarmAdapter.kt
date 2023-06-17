package com.example.childapp

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList

class AlarmAdapter(private val context: Context, private val layout: Int, private val alarmList: ArrayList<Model2>) : BaseAdapter() {
    override fun getCount(): Int {
        return alarmList.size
    }

    override fun getItem(i: Int): Any {
        return alarmList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    private class ViewHolder {

        var txtTitle: TextView? = null
        var txtDate: TextView? = null
        var txtTime: TextView?=null
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View? {
        var alarm_item = view

        var holder = ViewHolder()
        if (alarm_item == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            alarm_item = inflater.inflate(layout, null)
            holder.txtTitle = alarm_item.findViewById(R.id.txt_Title)
            holder.txtDate = alarm_item.findViewById(R.id.txt_Date)
            holder.txtTime = alarm_item.findViewById(R.id.txt_Time)

            alarm_item.tag = holder
        } else {
            holder = alarm_item.tag as ViewHolder
        }

        val model = alarmList[i]
        holder.txtTitle!!.text = model.title
        holder.txtDate!!.text = model.date
        holder.txtTime!!.text = model.time

        return alarm_item
    }
}