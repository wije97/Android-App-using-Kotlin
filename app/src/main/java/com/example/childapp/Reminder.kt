package com.example.childapp

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_reminder.*
import kotlinx.android.synthetic.main.activity_reminder.*
import kotlinx.android.synthetic.main.update_dialog.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class Reminder : AppCompatActivity() {

    var mListViewAL: ListView? = null
    var mListAL: ArrayList<Model2>? = null
    var mAdapterAL: AlarmAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        addanothereventbtn.setOnClickListener{
            val intent= Intent(this, AddReminder::class.java)
            startActivity(intent)
        }

        mListViewAL = findViewById(R.id.listViewAL)
        mListAL = ArrayList()
        mAdapterAL = AlarmAdapter(this, R.layout.alarm_item, mListAL!!)
        mListViewAL!!.setAdapter(mAdapterAL)

        val cursor = AddReminder.mSQLiteHelper!!.getDataAM("SELECT * FROM AlarmData")
        mListAL!!.clear()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val title = cursor.getString(1)
            val date = cursor.getString(3)
            val time=cursor.getString(4)

            mListAL!!.add(Model2(id, title, date, time))
        }

        mAdapterAL!!.notifyDataSetChanged()
        if (mListAL!!.size == 0) {
            Toast.makeText(this, "No Reminders Added...!", Toast.LENGTH_SHORT).show()
        }

        mListViewAL!!.setOnItemLongClickListener(AdapterView.OnItemLongClickListener { adapterView, view, position, l ->

            val items =
                arrayOf<CharSequence>("Update", "Delete")
            val dialog =
                AlertDialog.Builder(this@Reminder)
            dialog.setTitle("Choose an action")
            dialog.setItems(
                items
            ) { dialogInterface, i ->
                if (i == 0) {
                    val c: Cursor =
                        AddReminder.mSQLiteHelper!!.getDataAM("SELECT id FROM AlarmData")
                    val arrID = ArrayList<Int>()
                    while (c.moveToNext()) {
                        arrID.add(c.getInt(0))
                    }

                    showDialogUpdate(this@Reminder, arrID[position])
                }
                if (i == 1) {
                    val c: Cursor =
                        AddReminder.mSQLiteHelper!!.getDataAM("SELECT id FROM AlarmData")
                    val arrID = ArrayList<Int>()
                    while (c.moveToNext()) {
                        arrID.add(c.getInt(0))
                    }
                    showDialogDelete(arrID[position])
                }
            }
            dialog.show()
            true
        })
    }

    private fun showDialogDelete(idRecord: Int) {
        val dialogDelete =
            AlertDialog.Builder(this@Reminder)
        dialogDelete.setTitle("Warning!!")
        dialogDelete.setMessage("Are you sure to delete?")
        dialogDelete.setPositiveButton(
            "OK"
        ) { dialogInterface, i ->
            try {
                AddReminder.mSQLiteHelper!!.deleteDataAM(idRecord)
                Toast.makeText(
                    this@Reminder,
                    "Delete successfully",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Log.e("error", e.message)
            }
            updateRecordList()
        }
        dialogDelete.setNegativeButton(
            "Cancel"
        ) { dialogInterface, i -> dialogInterface.dismiss() }
        dialogDelete.show()
    }

    private fun showDialogUpdate(activity: Activity, position: Int) {
        val dialog = Dialog(activity)
        dialog.setContentView(R.layout.update_dialog)
        dialog.setTitle("Update")
        var edtTitle: EditText = dialog.findViewById(R.id.et_updateTitle)
        var edtDes: EditText = dialog.findViewById(R.id.et_updateDes)
        var edtDate: TextView = dialog.findViewById(R.id.uphiddenDate)
        var edtTime: TextView = dialog.findViewById(R.id.uphiddenTime)
        var btnUpdate: Button = dialog.findViewById(R.id.btn_updateEV)

        val width = (activity.resources.displayMetrics.widthPixels * 0.95).toInt()

        val height = (activity.resources.displayMetrics.heightPixels * 0.7).toInt()
        dialog.getWindow()!!.setLayout(width, height)
        dialog.show()



        btnUpdate.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onClick(view: View?) {
                try {

                    val customCalendar = Calendar.getInstance()
                    customCalendar.set(
                        date_up.year, date_up.month, date_up.dayOfMonth, time_up.hour, time_up.minute, 0
                    )

                    edtDate.text = (date_up.year.toString() +"/" + date_up.month.toString() + "/" + date_up.dayOfMonth.toString())
                    edtTime.text=(time_up.hour.toString() + ":" + time_up.minute.toString())

                    val customTime = customCalendar.timeInMillis
                    val currentTime = System.currentTimeMillis()
                    if (customTime > currentTime) {
                        val data = Data.Builder().putInt(NotifyWork.NOTIFICATION_ID, 0).build()
                        val delay = customTime - currentTime
                        scheduleNotification(delay, data)

                        AddReminder.mSQLiteHelper!!.updateDataAM(
                            edtTitle.text.toString().trim { it <= ' ' },
                            edtDes.text.toString().trim { it <= ' ' },
                            edtDate.text.toString().trim { it <= ' ' },
                            edtTime.text.toString().trim { it <= ' ' },
                            position
                        )
                        dialog.dismiss()
                        Toast.makeText(applicationContext, "Update Successfull", Toast.LENGTH_SHORT).show()


                    } else {
                        val errorNotificationSchedule = "Schedule notification: Incorrect Date and Time"
                        Snackbar.make(add_reminder, errorNotificationSchedule, Snackbar.LENGTH_LONG).show()
                    }

                } catch (error: java.lang.Exception) {
                    Log.e("Update error", error.message)
                }
                updateRecordList()
            }
        })
    }

    private fun updateRecordList() {
        val cursor = AddReminder.mSQLiteHelper!!.getDataAM("SELECT * FROM AlarmData")
        mListAL!!.clear()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val title = cursor.getString(1)
            val date = cursor.getString(2)
            val time=cursor.getString(3)

            mListAL!!.add(Model2(id, title, date, time))
        }
        mAdapterAL!!.notifyDataSetChanged()
    }

    private fun scheduleNotification(delay: Long, data: Data) {
        val notificationWork = OneTimeWorkRequest.Builder(NotifyWork::class.java)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS).setInputData(data).build()

        val instanceWorkManager = WorkManager.getInstance(this)
        instanceWorkManager.beginUniqueWork(NotifyWork.NOTIFICATION_WORK, ExistingWorkPolicy.REPLACE, notificationWork).enqueue()
    }
}