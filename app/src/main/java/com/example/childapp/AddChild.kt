package com.example.childapp

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_add_child.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat


class AddChild : AppCompatActivity() {

    var mEditFName: EditText? = null
    var mEditLName: EditText? = null
    var mEditDoB: TextView? = null
    var mBtnAdd: Button? = null
    var mBtnPhotoAdd: ImageButton? = null
    var mBtnSetDoB: ImageButton? = null
    var mImageView: ImageView? = null
    var rbGender : String?=null

    private var countOfMonths: String?=null
    private var countOfYears:String?=null
    val REQUEST_CODE_GALLERY = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_child)

        var profileCounts: Int = mSQLiteHelper!!.getProfilesCount()
        mSQLiteHelper!!.close()

        mEditFName = findViewById(R.id.et_baby_fname)
        mEditLName = findViewById(R.id.et_baby_lname)
        mEditDoB = findViewById(R.id.tv_set_dob)
        mImageView = findViewById(R.id.add_profile_photo)
        mBtnPhotoAdd = findViewById(R.id.add_photo_btn)
        mBtnSetDoB = findViewById(R.id.btn__setDOB)
        mBtnAdd = findViewById(R.id.addbtn)

        mEditDoB=this.tv_set_dob

        val curruntDate = SimpleDateFormat("dd").format(System.currentTimeMillis()).toInt()
        val curruntMonth = SimpleDateFormat("MM").format(System.currentTimeMillis()).toInt()
        val curruntYear = SimpleDateFormat("YYYY").format(System.currentTimeMillis()).toInt()

        fun age(year:Int, month:Int,dayOfMonth:Int): String{

            val birthDate:String
            val Monthset = month + 1

            birthDate = year.toString() + "/" + Monthset.toString() + "/" + dayOfMonth.toString()

            countOfYears = year.toString()
            countOfMonths = Monthset.toString()

            return birthDate
        }

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            mEditDoB!!.text = age(year, month, dayOfMonth)
        },curruntYear,curruntMonth - 1,curruntDate)

        mBtnSetDoB!!.setOnClickListener{
            datePickerDialog.show()
        }

       // mSQLiteHelper = SQLiteHelper(this, "RECORDDB.sqlite", null, 1)

        //mSQLiteHelper!!.queryData("CREATE TABLE IF NOT EXISTS RECORD(id INTEGER PRIMARY KEY AUTOINCREMENT, fname VARCHAR, lname VARCHAR, dob VARCHAR, gender VARCHAR, image BLOB, yearcount VARCHAR, monthcount VARCHAR)")


        mBtnPhotoAdd!!.setOnClickListener(View.OnClickListener {
            ActivityCompat.requestPermissions(
                this@AddChild, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_GALLERY
            )
        })

        mBtnAdd!!.setOnClickListener(View.OnClickListener {

            try {
                radioButtonValue()

                mSQLiteHelper!!.insertData(
                    mEditFName!!.getText().toString().trim { it <= ' ' },
                    mEditLName!!.getText().toString().trim { it <= ' ' },
                    mEditDoB!!.getText().toString().trim { it <= ' ' },
                    rbGender!!.toString().trim { it <= ' ' },
                    imageViewToByte(mImageView),
                    countOfYears!!.toString().trim { it <= ' ' },
                    countOfMonths!!.toString().trim { it <= ' ' })

                Toast.makeText(this@AddChild, "Added successfully", Toast.LENGTH_SHORT).show()

                mSQLiteHelper!!.queryData("CREATE TABLE IF NOT EXISTS vaccinCheckTable(vc_id INTEGER NOT NULL PRIMARY KEY  AUTOINCREMENT, BCG REAL, OPV1 REAL, OPV2 REAL,OPV3 REAL,MMR1 REAL,LJE REAL, OPV4 REAL,MMR2 REAL, OPV5 REAL, HPV1 REAL, HPV2 REAL, aTD REAL, RUBELLA REAL,child_id INTEGER , FOREIGN KEY(child_id) REFERENCES ChildData(id))")
                AddChild.mSQLiteHelper!!.vaccinCheck( 0, 0,0, 0,0, 0,0, 0,0, 0,0, 0,0,  ++profileCounts)

                mSQLiteHelper!!.queryData("CREATE TABLE IF NOT EXISTS vaccineDateTable(vc_id INTEGER NOT NULL PRIMARY KEY  AUTOINCREMENT, BCG VARCHAR, OPV1 VARCHAR, OPV2 VARCHAR,OPV3 VARCHAR,MMR1 VARCHAR,LJE VARCHAR, OPV4 VARCHAR,MMR2 VARCHAR, OPV5 VARCHAR, HPV1 VARCHAR, HPV2 VARCHAR, aTD VARCHAR, RUBELLA VARCHAR, child_id INTEGER , FOREIGN KEY(child_id) REFERENCES ChildData(id))")
                AddChild.mSQLiteHelper!!.vaccineDate( "DD/MM/YY", "DD/MM/YY","DD/MM/YY", "DD/MM/YY","DD/MM/YY", "DD/MM/YY","DD/MM/YY", "DD/MM/YY","DD/MM/YY", "DD/MM/YY","DD/MM/YY", "DD/MM/YY", "DD/MM/YY",profileCounts)

                mEditFName!!.setText("")
                mEditLName!!.setText("")
                mEditDoB!!.setText("")
                mImageView!!.setImageResource(R.drawable.profile)

                startActivity(Intent(this@AddChild, ChooseBaby::class.java))

            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
                galleryIntent.type = "image/*"
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY)
            } else {
                Toast.makeText(this, "Don't have permission to access file location", Toast.LENGTH_SHORT).show()
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK) {
            val imageUri = data!!.data
            CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(this)
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                mImageView!!.setImageURI(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        var mSQLiteHelper: SQLiteHelper? = null
        fun imageViewToByte(image: ImageView?): ByteArray {
            val bitmap = (image!!.drawable as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            return stream.toByteArray()
        }
    }
    private fun radioButtonValue(){

        if(rb_boy.isChecked){
            rbGender= rb_boy.getText().toString()
        }
        else{
            rbGender=rb_girl.getText().toString()
        }

    }
}