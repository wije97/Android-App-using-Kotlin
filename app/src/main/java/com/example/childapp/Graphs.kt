package com.example.childapp

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.activity_graphs.*
import kotlinx.android.synthetic.main.activity_growth_tracker.*
import java.util.ArrayList

class Graphs : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graphs)

        var id=intent.getStringExtra("id")

        btn_weight_age.setOnClickListener {
            val intent = Intent(this@Graphs, weight_and_age::class.java)
            intent.putExtra("id",id)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        btn_height_age.setOnClickListener {
            val intent = Intent(this@Graphs, height_and_age::class.java)
            intent.putExtra("id",id)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        btn_bmi_age.setOnClickListener {
            val intent = Intent(this@Graphs, bmi_and_age::class.java)
            intent.putExtra("id",id)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        btn_weight_height.setOnClickListener {
            val intent = Intent(this@Graphs, weight_and_height::class.java)
            intent.putExtra("id",id)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}
