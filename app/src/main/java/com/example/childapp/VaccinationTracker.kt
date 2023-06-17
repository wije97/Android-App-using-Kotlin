package com.example.childapp

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.Entry
import kotlinx.android.synthetic.main.activity_vaccination_tracker.*
import kotlinx.android.synthetic.main.activity_weight_and_age.*
import java.util.ArrayList


class VaccinationTracker : AppCompatActivity() {

    var txtVCID: EditText?=null
    var myHelper: SQLiteHelper? = null
    var db: SQLiteDatabase? = null

    var x1 = 0
    var x2 = 0
    var x3 = 0
    var x4 = 0
    var x5 = 0
    var x6 = 0
    var x7 = 0
    var x8 = 0
    var x9 = 0
    var x10 = 0
    var x11 = 0
    var x12 = 0
    var x13 = 0

    private var date1:String?=null
    private var date2:String?=null
    private var date3:String?=null
    private var date4:String?=null
    private var date5:String?=null
    private var date6:String?=null
    private var date7:String?=null
    private var date8:String?=null
    private var date9:String?=null
    private var date10:String?=null
    private var date11:String?=null
    private var date12:String?=null
    private var date13:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vaccination_tracker)
        checkBoxVals()
        getvcCheck()

        getvcDate()

        txtVCID=findViewById(R.id.txt_CID)

        var idin = intent.getStringExtra("id")
        txtVCID!!.setText(idin)
    }

    private fun checkBoxVals(){
        chckbx1.setOnClickListener(){
            if(chckbx1.isChecked){
                x1 = 1
                Toast.makeText(this@VaccinationTracker, "Checked", Toast.LENGTH_SHORT).show()
            }else if(!chckbx1.isChecked){
                x1 = 0
                Toast.makeText(this@VaccinationTracker, "Unchecked", Toast.LENGTH_SHORT).show()
            }
        }

        chckbx2.setOnClickListener(){
            if(chckbx2.isChecked){
                 x2 = 1
                Toast.makeText(this@VaccinationTracker, "Checked", Toast.LENGTH_SHORT).show()
            }else if(!chckbx2.isChecked){
                 x2 = 0
                Toast.makeText(this@VaccinationTracker, "Unchecked", Toast.LENGTH_SHORT).show()
            }
        }
        chckbx3.setOnClickListener(){
            if(chckbx3.isChecked){
                x3 = 1
                Toast.makeText(this@VaccinationTracker, "Checked", Toast.LENGTH_SHORT).show()
            }else if(!chckbx3.isChecked){
                x3 = 0
                Toast.makeText(this@VaccinationTracker, "Unchecked", Toast.LENGTH_SHORT).show()
            }
        }

        chckbx4.setOnClickListener(){
            if(chckbx4.isChecked){
                x4 = 1
                Toast.makeText(this@VaccinationTracker, "Checked", Toast.LENGTH_SHORT).show()
            }else if(!chckbx4.isChecked){
                x4 = 0
                Toast.makeText(this@VaccinationTracker, "Unchecked", Toast.LENGTH_SHORT).show()
            }
        }
        chckbx5.setOnClickListener(){
            if(chckbx5.isChecked){
                x5 = 1
                Toast.makeText(this@VaccinationTracker, "Checked", Toast.LENGTH_SHORT).show()
            }else if(!chckbx5.isChecked){
                x5 = 0
                Toast.makeText(this@VaccinationTracker, "Unchecked", Toast.LENGTH_SHORT).show()
            }
        }

        chckbx6.setOnClickListener(){
            if(chckbx6.isChecked){
                x6 = 1
                Toast.makeText(this@VaccinationTracker, "Checked", Toast.LENGTH_SHORT).show()
            }else if(!chckbx6.isChecked){
                x6 = 0
                Toast.makeText(this@VaccinationTracker, "Unchecked", Toast.LENGTH_SHORT).show()
            }
        }
        chckbx7.setOnClickListener(){
            if(chckbx7.isChecked){
                x7 = 1
                Toast.makeText(this@VaccinationTracker, "Checked", Toast.LENGTH_SHORT).show()
            }else if(!chckbx7.isChecked){
                x7 = 0
                Toast.makeText(this@VaccinationTracker, "Unchecked", Toast.LENGTH_SHORT).show()
            }
        }

        chckbx8.setOnClickListener(){
            if(chckbx8.isChecked){
                x8 = 1
                Toast.makeText(this@VaccinationTracker, "Checked", Toast.LENGTH_SHORT).show()
            }else if(!chckbx8.isChecked){
                x8 = 0
                Toast.makeText(this@VaccinationTracker, "Unchecked", Toast.LENGTH_SHORT).show()
            }
        }
        chckbx9.setOnClickListener(){
            if(chckbx9.isChecked){
                x9 = 1
                Toast.makeText(this@VaccinationTracker, "Checked", Toast.LENGTH_SHORT).show()
            }else if(!chckbx9.isChecked){
                x9 = 0
                Toast.makeText(this@VaccinationTracker, "Unchecked", Toast.LENGTH_SHORT).show()
            }
        }

        chckbx10.setOnClickListener(){
            if(chckbx10.isChecked){
                x10 = 1
                Toast.makeText(this@VaccinationTracker, "Checked", Toast.LENGTH_SHORT).show()
            }else if(!chckbx10.isChecked){
                x10 = 0
                Toast.makeText(this@VaccinationTracker, "Unchecked", Toast.LENGTH_SHORT).show()
            }
        }
        chckbx11.setOnClickListener(){
            if(chckbx11.isChecked){
                x11 = 1
                Toast.makeText(this@VaccinationTracker, "Checked", Toast.LENGTH_SHORT).show()
            }else if(!chckbx11.isChecked){
                x11 = 0
                Toast.makeText(this@VaccinationTracker, "Unchecked", Toast.LENGTH_SHORT).show()
            }
        }

        chckbx12.setOnClickListener(){
            if(chckbx12.isChecked){
                x12 = 1
                Toast.makeText(this@VaccinationTracker, "Checked", Toast.LENGTH_SHORT).show()
            }else if(!chckbx12.isChecked){
                x12 = 0
                Toast.makeText(this@VaccinationTracker, "Unchecked", Toast.LENGTH_SHORT).show()
            }
        }
        chckbx13.setOnClickListener(){
            if(chckbx13.isChecked){
                x13 = 1
                Toast.makeText(this@VaccinationTracker, "Checked", Toast.LENGTH_SHORT).show()
            }else if(!chckbx13.isChecked){
                x13 = 0
                Toast.makeText(this@VaccinationTracker, "Unchecked", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        return if (id == R.id.action_save) {

            val bcg = x1.toString().toInt()
            val opv1 = x2.toString().toInt()
            val opv2 = x3.toString().toInt()
            val opv3 = x4.toString().toInt()
            val mmr1 = x5.toString().toInt()
            val lje = x6.toString().toInt()
            val opv4 = x7.toString().toInt()
            val mmr2 = x8.toString().toInt()
            val opv5 = x9.toString().toInt()
            val hpv1 = x10.toString().toInt()
            val hpv2 = x11.toString().toInt()
            val atd = x12.toString().toInt()
            val rub = x13.toString().toInt()
            val txtvcid = txtVCID!!.text.toString().toInt()
            AddChild.mSQLiteHelper!!.vaccinCheckUp(bcg,opv1,opv2,opv3,mmr1,lje,opv4,mmr2,opv5,hpv1,hpv2,atd,rub,txtvcid)

            checkDate()
            val bcgDate = date1.toString()
            val opv1Date = date2.toString()
            val opv2Date = date3.toString()
            val opv3Date = date4.toString()
            val mmr1Date = date5.toString()
            val ljeDate = date6.toString()
            val opv4Date = date7.toString()
            val mmr2Date = date8.toString()
            val opv5Date = date9.toString()
            val hpv1Date = date10.toString()
            val hpv2Date = date11.toString()
            val atdDate = date12.toString()
            val rubDate = date13.toString()
            val txtvcdId = txtVCID!!.text.toString().toInt()
            AddChild.mSQLiteHelper!!.vaccineDateUp(bcgDate,opv1Date,opv2Date,opv3Date,mmr1Date,ljeDate,opv4Date,mmr2Date,opv5Date,hpv1Date,hpv2Date,atdDate,rubDate,txtvcdId)

            Toast.makeText(this@VaccinationTracker, "Done", Toast.LENGTH_SHORT).show()
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun getvcCheck(){

        val cursor = AddChild.mSQLiteHelper!!.getDataVaccine("SELECT * FROM vaccinCheckTable ")

        while (cursor.moveToNext()) {
            val bcg = cursor.getInt(1)
            val opv1 = cursor.getInt(2)
            val opv2 = cursor.getInt(3)
            val opv3 = cursor.getInt(4)
            val mmr1 = cursor.getInt(5)
            val lje = cursor.getInt(6)
            val opv4 = cursor.getInt(7)
            val mmr2 = cursor.getInt(8)
            val opv5 = cursor.getInt(9)
            val hpv1 = cursor.getInt(10)
            val hpv2 = cursor.getInt(11)
            val atd = cursor.getInt(12)
            val rub = cursor.getInt(13)

            if(bcg==1){
                chckbx1.isChecked=true
                x1=1
            }

            if(opv1==1){
                chckbx2.isChecked=true
                x2=1
            }
            if(opv2==1){
                chckbx3.isChecked=true
                x3=1
            }

            if(opv3==1){
                chckbx4.isChecked=true
                x4=1
            }
            if(mmr1==1){
                chckbx5.isChecked=true
                x5=1
            }

            if(lje==1){
                chckbx6.isChecked=true
                x6=1
            }
            if(opv4==1){
                chckbx7.isChecked=true
                x7=1
            }

            if(mmr2==1){
                chckbx8.isChecked=true
                x8=1
            }
            if(opv5==1){
                chckbx9.isChecked=true
                x9=1
            }

            if(hpv1==1){
                chckbx10.isChecked=true
                x10=1
            }
            if(hpv2==1){
                chckbx11.isChecked=true
                x11=1
            }

            if(atd==1){
                chckbx12.isChecked=true
                x12=1
            }
            if(rub==1){
                chckbx13.isChecked=true
                x13=1
            }

        }
    }
    private fun checkDate(){

        date1=checkDate1.text.toString()
        date2=checkDate2.text.toString()
        date3=checkDate3.text.toString()
        date4=checkDate4.text.toString()
        date5=checkDate5.text.toString()
        date6=checkDate6.text.toString()
        date7=checkDate7.text.toString()
        date8=checkDate8.text.toString()
        date9=checkDate9.text.toString()
        date10=checkDate10.text.toString()
        date11=checkDate11.text.toString()
        date12=checkDate12.text.toString()
        date13=checkDate13.text.toString()

    }

    private fun getvcDate(){

        //val cursor = AddChild.mSQLiteHelper!!.getDataVaccineDate("SELECT * FROM vaccineDateTable WHERE child_id='"+ txtVCID?.text.toString()+ "'")
        val cursor = AddChild.mSQLiteHelper!!.getDataVaccineDate("SELECT * FROM vaccineDateTable ")

        while (cursor.moveToNext()) {
            val bcgD = cursor.getString(1)
            val opv1D = cursor.getString(2)
            val opv2D = cursor.getString(3)
            val opv3D = cursor.getString(4)
            val mmr1D = cursor.getString(5)
            val ljeD = cursor.getString(6)
            val opv4D = cursor.getString(7)
            val mmr2D = cursor.getString(8)
            val opv5D = cursor.getString(9)
            val hpv1D = cursor.getString(10)
            val hpv2D = cursor.getString(11)
            val atdD = cursor.getString(12)
            val rubeD = cursor.getString(13)

            checkDate1.setText(bcgD.toString())
            checkDate2.setText(opv1D.toString())
            checkDate3.setText(opv2D.toString())
            checkDate4.setText(opv3D.toString())
            checkDate5.setText(mmr1D.toString())
            checkDate6.setText(ljeD.toString())
            checkDate7.setText(opv4D.toString())
            checkDate8.setText(mmr2D.toString())
            checkDate9.setText(opv5D.toString())
            checkDate10.setText(hpv1D.toString())
            checkDate11.setText(hpv2D.toString())
            checkDate12.setText(atdD.toString())
            checkDate13.setText(rubeD.toString())

        }
    }
}
