package com.example.childapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.ArrayList


class loginpage : AppCompatActivity() {

    private val TAG = "loginpage"

    private var email: String? = null
    private var password: String? = null

    private var tvForgotPassword: TextView? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnLogin: Button? = null
    private var btnCreateNew: Button? = null
    private var mProgressBar: ProgressDialog? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginpage)

        initialise()
    }

    private fun initialise() {
        tvForgotPassword = findViewById<View>(R.id.tv_forgotPassword) as TextView
        etEmail = findViewById<View>(R.id.et_email) as EditText
        etPassword = findViewById<View>(R.id.et_password) as EditText
        btnLogin = findViewById<View>(R.id.loginbtn) as Button
        btnCreateNew = findViewById<View>(R.id.btn_createnew) as Button
        mProgressBar = ProgressDialog(this)

        mAuth = FirebaseAuth.getInstance()

        tvForgotPassword!!
            .setOnClickListener { startActivity(Intent(this@loginpage,
                ForgotPassword::class.java)) }

        btnCreateNew!!
            .setOnClickListener { startActivity(Intent(this@loginpage,
                Signinpage::class.java)) }

        btnLogin!!.setOnClickListener(View.OnClickListener {
            loginUser()

        })
    }
    private fun loginUser() {

        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            mProgressBar!!.setMessage("Loging in...")
            mProgressBar!!.show()

            Log.d(TAG, "Logging in user.")

            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->

                    mProgressBar!!.hide()

                    if (task.isSuccessful) {

                        Log.d(TAG, "signInWithEmail:success")
                        updateUI()
                    } else {

                        Log.e(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this@loginpage, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Please check the Email and Password!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateUI() {
        AddChild.mSQLiteHelper = SQLiteHelper(this, "RECORDDB.sqlite", null, 1)
        AddChild.mSQLiteHelper!!.queryData("CREATE TABLE IF NOT EXISTS ChildData(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, fname VARCHAR, lname VARCHAR, dob VARCHAR, gender VARCHAR, image BLOB, yearcount VARCHAR, monthcount VARCHAR)")

        startActivity(Intent(this@loginpage, ChooseBaby::class.java))
    }
}
