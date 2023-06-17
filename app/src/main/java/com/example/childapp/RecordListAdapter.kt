@file:Suppress("SENSELESS_COMPARISON")

package com.example.childapp

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class RecordListAdapter(private val context: Context, private val layout: Int, private val recordList: ArrayList<Model>) : BaseAdapter() {
    override fun getCount(): Int {
        return recordList.size
    }

    override fun getItem(i: Int): Any {
        return recordList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    private class ViewHolder {
        var imageView: ImageView? = null
        var txtName: TextView? = null
        var txtDoB: TextView? = null
        var txtGender: TextView? = null
        var txtYear: TextView?=null
        var txtMonth: TextView?=null
        var txtCID: TextView?=null
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View? {
        var row = view

        var holder = ViewHolder()
        if (row == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            row = inflater.inflate(layout, null)
            holder.txtName = row.findViewById(R.id.txt_FName)
            holder.txtDoB = row.findViewById(R.id.txt_DoB)
            holder.txtGender = row.findViewById(R.id.txt_Gender)
            holder.imageView = row.findViewById(R.id.imgIcon)
            holder.txtYear = row.findViewById(R.id.txt_Year)
            holder.txtMonth = row.findViewById(R.id.txt_Month)
            holder.txtCID = row.findViewById(R.id.txt_ID)

            row.tag = holder
        } else {
            holder = row.tag as ViewHolder
        }

        val model = recordList[i]
        holder.txtName!!.text = model.fname
        holder.txtDoB!!.text = model.dob
        holder.txtGender!!.text = model.gender

        val recordImage = model.image
        val bitmap = BitmapFactory.decodeByteArray(recordImage, 0, recordImage.size)
        holder.imageView!!.setImageBitmap(bitmap)

        holder.txtYear!!.text = model.yearcount
        holder.txtMonth!!.text = model.monthcount
        holder.txtCID!!.text = model.id.toString()

        return row
    }
}


