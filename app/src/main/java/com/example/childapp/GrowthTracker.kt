package com.example.childapp

import android.app.DatePickerDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_child.btn__setDOB
import kotlinx.android.synthetic.main.activity_add_child.tv_set_dob
import kotlinx.android.synthetic.main.activity_growth_tracker.*
import java.text.SimpleDateFormat


class GrowthTracker : AppCompatActivity() {

    var txtNameI: TextView? = null
    var txtDoBI:TextView? = null
    var etYearView: EditText?=null
    var etMonthView:EditText?=null
    var imageBaby:ImageView?=null
    var txtCID:EditText?=null


    private var weight: String?=null
    private var height: String?=null
    private var dateofmes: String?=null
    private var monthscount: String?=null
    private var id:Int?=null

    private var etWeight: EditText? = null
    private var etHeight: EditText? = null
    private var etDateofMeas: TextView? = null
    private var btnMdate: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_growth_tracker)

        txtNameI = findViewById(R.id.tv_baby_name)
        txtDoBI = findViewById(R.id.tv_baby_dob)
        etYearView = findViewById(R.id.tv_getBDYeear)
        etMonthView = findViewById(R.id.tv_getBDMonth)
        imageBaby=findViewById(R.id.iv_baby)
        txtCID=findViewById(R.id.txt_CID)


        var namein = intent.getStringExtra("name")
        var doBin = intent.getStringExtra("dob")
        var yearin=intent.getStringExtra("year")
        var monthin=intent.getStringExtra("month")
        var idin = intent.getStringExtra("id")


        val extras = intent.extras
        val b = extras!!.getByteArray("picture")
        val bmp = BitmapFactory.decodeByteArray(b, 0, b!!.size)

        txtNameI!!.text=namein
        txtDoBI!!.text=doBin
        etYearView!!.setText(yearin)
        etMonthView!!.setText(monthin)
        imageBaby!!.setImageBitmap(bmp)
        txtCID!!.setText(idin)


        etDateofMeas = this.tv_set_dob
        btnMdate = this.btn__setDOB

        val curruntDate = SimpleDateFormat("dd").format(System.currentTimeMillis()).toInt()
        val curruntMonth = SimpleDateFormat("MM").format(System.currentTimeMillis()).toInt()
        val curruntYear = SimpleDateFormat("YYYY").format(System.currentTimeMillis()).toInt()

        fun age(year:Int, month:Int,dayOfMonth:Int): String{

            val mesDate:String
            val Monthset = month + 1

            val birthYear:Int
            val birthMonth: Int

            birthYear = etYearView!!.text.toString().toInt()
            birthMonth = etMonthView!!.text.toString().toInt()

            var modCurrentMonth = Monthset
            var modCurrentYear = year

            val ageMonth: Int
            val ageYear: Int

            if(birthMonth > modCurrentMonth){
                modCurrentMonth += 12
                modCurrentYear -= 1
                ageMonth = modCurrentMonth - birthMonth
            }else{
                ageMonth = modCurrentMonth - birthMonth
            }

            ageYear = modCurrentYear - birthYear

            //val ageMonthx = ageMonth - 1

            val numberOfMonths: Int

            if (ageYear > 0 && ageYear <= 1 ){
                numberOfMonths = ageMonth + 12
            }else if (ageYear > 1 && ageYear <= 2 ){
                numberOfMonths = ageMonth + 24
            }else if (ageYear > 2 && ageYear <= 3 ){
                numberOfMonths = ageMonth + 36
            }else if (ageYear > 3 && ageYear <= 4 ){
                numberOfMonths = ageMonth + 48
            }else if (ageYear > 4 && ageYear <= 5 ){
                numberOfMonths = ageMonth + 60
            }else{
                numberOfMonths = ageMonth
            }

            monthscount = numberOfMonths.toString()


            mesDate = year.toString() + "/" + Monthset.toString() + "/" + dayOfMonth.toString()

            return mesDate
        }

        val datePickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener {View, year, month, dayOfMonth ->
            etDateofMeas!!.text = age(year, month, dayOfMonth)
        },curruntYear,curruntMonth - 1,curruntDate)

        btnMdate!!.setOnClickListener{
            datePickerDialog.show()
        }


        etWeight = findViewById<View>(R.id.et_weight) as EditText
        etHeight = findViewById<View>(R.id.et_height) as EditText

        btn_insert_data.setOnClickListener {
            addRecords()
        }

        btn_show_graphs.setOnClickListener {
            val intent = Intent(this@GrowthTracker, Graphs::class.java)
            intent.putExtra("id",txtCID!!.text.toString())
            startActivity(intent)
        }
    }

    private fun addRecords(){

        dateofmes = etDateofMeas?.text.toString()
        weight = etWeight?.text.toString()
        height = etHeight?.text.toString()
        id = txtCID?.text.toString().toInt()

        if(!TextUtils.isEmpty(dateofmes) && !TextUtils.isEmpty(weight)
            && !TextUtils.isEmpty(height)){

            var recorddt = RecordDT(dateofmes!!,weight!!,height!!,monthscount!!,id!!)
            AddChild.mSQLiteHelper!!.insertRecordData(recorddt)

            weightageMYHelper()
            heightageMYHelper()
            bmiageMYHelper()
            weightheightMYHelper()

            Toast.makeText(this@GrowthTracker, "Records added successfully", Toast.LENGTH_SHORT).show()

            etWeight!!.setText("")
            etHeight!!.setText("")
            etDateofMeas!!.setText("")

        }else{
            Toast.makeText(this@GrowthTracker, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun weightageMYHelper(){
        val aVal = monthscount!!.toString().toFloat()
        val wVal = weight!!.toString().toFloat()
        val cID= txtCID?.text.toString().toInt()
        AddChild.mSQLiteHelper!!.insertDataWA(aVal, wVal,cID)
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
    }

    private fun heightageMYHelper(){
        val aVal = monthscount!!.toString().toFloat()
        val hVal = height!!.toString().toFloat()
        val cID= txtCID?.text.toString().toInt()
        AddChild.mSQLiteHelper!!.insertDataHA(aVal, hVal,cID)
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
    }
    private fun weightheightMYHelper(){
        val hVal = height!!.toString().toFloat()
        val wVal = weight!!.toString().toFloat()
        val cID= txtCID?.text.toString().toInt()
        AddChild.mSQLiteHelper!!.insertDataWH(hVal, wVal,cID)
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
    }
    private fun bmiageMYHelper(){
        var BMIValue = weight.toString().toFloat()/(height.toString().toFloat() * height.toString().toFloat())

        val aVal =monthscount!!.toString().toFloat()
        val bmiVal = BMIValue
        val cID= txtCID?.text.toString().toInt()
        AddChild.mSQLiteHelper!!.insertDataBA(aVal, bmiVal,cID)
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
    }
}
