package com.example.childapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_growth_tracker.*
import kotlinx.android.synthetic.main.activity_main_page.*
import kotlinx.android.synthetic.main.activity_reminder.*
import java.io.ByteArrayOutputStream

class MainPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        btn_Growth.setOnClickListener {

            var name = intent.getStringExtra("name")
            var doB = intent.getStringExtra("dob")
            var year=intent.getStringExtra("year")
            var month=intent.getStringExtra("month")
            var id=intent.getStringExtra("id")

            val extras = intent.extras
            val b0 = extras!!.getByteArray("picture")
            val bmp = BitmapFactory.decodeByteArray(b0, 0, b0!!.size)

            imageintent.setImageBitmap(bmp)

            val drawable: Drawable = imageintent.getDrawable()
            val bitmap = (drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val b: ByteArray = baos.toByteArray()

            val intent = Intent(this@MainPage, GrowthTracker::class.java)

            intent.putExtra("name", name)
            intent.putExtra("dob", doB)
            intent.putExtra("year", year)
            intent.putExtra("month", month)
            intent.putExtra("id",id)
            intent.putExtra("picture", b)
            startActivity(intent)
        }
        btn_reminder.setOnClickListener {
            AddReminder.mSQLiteHelper = SQLiteHelper(this, "RECORDDB.sqlite", null, 1)
            AddReminder.mSQLiteHelper!!.queryData2("CREATE TABLE IF NOT EXISTS AlarmData(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, title VARCHAR, des VARCHAR, date VARCHAR, time VARCHAR)")
            startActivity(Intent(this@MainPage, Reminder::class.java))
        }

        btn_vaccination.setOnClickListener {

            var id=intent.getStringExtra("id")

            val intent = Intent(this@MainPage, VaccinationTracker::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }

        btn_food.setOnClickListener {

            startActivity(Intent(this@MainPage, FoodTips::class.java))
        }

        btn_help.setOnClickListener {

            startActivity(Intent(this@MainPage, HealthTips::class.java))
        }
    }

}
