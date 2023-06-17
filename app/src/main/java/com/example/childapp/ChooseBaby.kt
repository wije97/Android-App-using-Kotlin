package com.example.childapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_choose_baby.*
import java.io.ByteArrayOutputStream
import java.util.*


class ChooseBaby : AppCompatActivity() {

    var mListView: ListView? = null
    var mList: ArrayList<Model>? = null
    var mAdapter: RecordListAdapter? = null

    var imageView: ImageView? = null
    var txtName: TextView? = null
    var txtDoB: TextView? = null
    var txtGender: TextView? = null
    var txtYear: TextView?=null
    var txtMonth: TextView?=null
    var txtCID: TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_baby)

        addanotherbtn.setOnClickListener{
            val intent=Intent(this, AddChild::class.java)
                startActivity(intent)
        }

        mListView = findViewById(R.id.listView)
        mList = ArrayList()
        mAdapter = RecordListAdapter(this, R.layout.row, mList!!)
        mListView!!.setAdapter(mAdapter)

        val cursor = AddChild.mSQLiteHelper!!.getData("SELECT * FROM ChildData")
        mList!!.clear()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val fname = cursor.getString(1)
            val lname = cursor.getString(2)
            val dob = cursor.getString(3)
            val gender=cursor.getString(4)
            val image = cursor.getBlob(5)
            val yearcount=cursor.getString(6)
            val monthcount=cursor.getString(7)

            mList!!.add(Model(id, fname, lname, dob, gender, image, yearcount, monthcount))
        }

        mAdapter!!.notifyDataSetChanged()
        if (mList!!.size == 0) {
            Toast.makeText(this, "Add your Baby...!", Toast.LENGTH_SHORT).show()
        }

        mListView!!.setOnItemClickListener(OnItemClickListener { adapterView, view, position, id->

            var row = view

            txtName = row.findViewById(R.id.txt_FName)
            txtDoB = row.findViewById(R.id.txt_DoB)
            txtGender = row.findViewById(R.id.txt_Gender)
            imageView = row.findViewById(R.id.imgIcon)
            txtYear = row.findViewById(R.id.txt_Year)
            txtMonth = row.findViewById(R.id.txt_Month)
            txtCID = row.findViewById(R.id.txt_ID)

            val drawable: Drawable = imageView!!.getDrawable()
            val bitmap = (drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val b: ByteArray = baos.toByteArray()

            val intent =  Intent(this@ChooseBaby, MainPage::class.java)
            intent.putExtra("name", txtName!!.text.toString())
            intent.putExtra("dob", txtDoB!!.text.toString())
            intent.putExtra("year", txtYear!!.text.toString())
            intent.putExtra("month", txtMonth!!.text.toString())
            intent.putExtra("id",txtCID!!.text.toString())
            intent.putExtra("picture", b)
            startActivity(intent) })

        mListView!!.setOnItemLongClickListener(OnItemLongClickListener { adapterView, view, position, l ->

            val items = arrayOf<CharSequence>("Delete")
            val dialog = AlertDialog.Builder(this@ChooseBaby)
            dialog.setItems(items) { dialogInterface, i ->
                val c = AddChild.mSQLiteHelper!!.getData("SELECT id FROM ChildData")
                val arrID = ArrayList<Int>()
                while (c.moveToNext()) {
                    arrID.add(c.getInt(0))
                }
                showDialogDelete(arrID[position])
            }
            dialog.show()
            true
        })
    }

    private fun showDialogDelete(idRecord: Int) {
        val dialogDelete = AlertDialog.Builder(this@ChooseBaby)
        dialogDelete.setTitle("Warning!!")
        dialogDelete.setMessage("Are you sure to delete?")
        dialogDelete.setPositiveButton("OK") { dialogInterface, i ->
            try {
                AddChild.mSQLiteHelper!!.deleteData(idRecord)
                AddChild.mSQLiteHelper!!.deleteDatavc(idRecord)
                Toast.makeText(this@ChooseBaby, "Delete successfully", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("error", e.message)
            }
            updateRecordList()
        }
        dialogDelete.setNegativeButton("Cancel") { dialogInterface, i -> dialogInterface.dismiss() }
        dialogDelete.show()
    }

    private fun updateRecordList() {
        val cursor = AddChild.mSQLiteHelper!!.getData("SELECT * FROM ChildData")
        mList!!.clear()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val fname = cursor.getString(1)
            val lname = cursor.getString(2)
            val dob = cursor.getString(3)
            val gender=cursor.getString(4)
            val image = cursor.getBlob(5)
            val yearcount=cursor.getString(6)
            val monthcount=cursor.getString(7)
            mList!!.add(Model(id, fname, lname, dob, gender, image, yearcount, monthcount))
        }
        mAdapter!!.notifyDataSetChanged()
    }
}