package com.example.childapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.childapp.NotifyWork.Companion.NOTIFICATION_ID
import com.example.childapp.NotifyWork.Companion.NOTIFICATION_WORK
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_reminder.*
import java.util.*
import java.util.concurrent.TimeUnit

class AddReminder : AppCompatActivity() {

    var mEditTitle: EditText? = null
    var mEditDes: EditText? = null
    var mEditDate: TextView? = null
    var mEditTime: TextView? = null
    var mBtnSave: Button? = null


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)

        mEditTitle = findViewById(R.id.et_Title)
        mEditDes = findViewById(R.id.et_Des)
        mEditDate = findViewById(R.id.hiddenDate)
        mEditTime = findViewById(R.id.hiddenTime)

        mBtnSave = findViewById(R.id.btn_saveEV)

        mBtnSave!!.setOnClickListener(View.OnClickListener {

            try {
                val customCalendar = Calendar.getInstance()
                customCalendar.set(
                    date_p.year, date_p.month, date_p.dayOfMonth, time_p.hour, time_p.minute, 0
                )

                mEditDate!!.text = (date_p.year.toString() +"/" + date_p.month.toString() + "/" + date_p.dayOfMonth.toString())
                mEditTime!!.text=(time_p.hour.toString() + ":" + time_p.minute.toString())

                val customTime = customCalendar.timeInMillis
                val currentTime = System.currentTimeMillis()
                if (customTime > currentTime) {
                    val data = Data.Builder().putInt(NOTIFICATION_ID, 0).build()
                    val delay = customTime - currentTime
                    scheduleNotification(delay, data)

                    mSQLiteHelper!!.insertDataAM(
                        mEditTitle!!.getText().toString().trim { it <= ' ' },
                        mEditDes!!.getText().toString().trim { it <= ' ' },
                        mEditDate!!.getText().toString().trim { it <= ' ' },
                        mEditTime!!.getText().toString().trim { it <= ' ' }
                    )

                    Toast.makeText(this@AddReminder, "Reminder Added...!", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this@AddReminder, Reminder::class.java))

                    /*val titleNotificationSchedule = getString(R.string.notification_schedule_title)
                    val patternNotificationSchedule = getString(R.string.notification_schedule_pattern)
                    Snackbar.make(
                        add_reminder,
                        titleNotificationSchedule + SimpleDateFormat(
                            patternNotificationSchedule, Locale.getDefault()
                        ).format(customCalendar.time).toString(),
                        Snackbar.LENGTH_LONG
                    ).show()*/

                } else {
                    val errorNotificationSchedule = "Schedule notification: Incorrect Date and Time"
                    Snackbar.make(add_reminder, errorNotificationSchedule, Snackbar.LENGTH_LONG).show()
                }

            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    companion object {
        var mSQLiteHelper: SQLiteHelper? = null
    }

    private fun scheduleNotification(delay: Long, data: Data) {
        val notificationWork = OneTimeWorkRequest.Builder(NotifyWork::class.java)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS).setInputData(data).build()

        val instanceWorkManager = WorkManager.getInstance(this)
        instanceWorkManager.beginUniqueWork(NOTIFICATION_WORK, ExistingWorkPolicy.REPLACE, notificationWork).enqueue()
    }
}
