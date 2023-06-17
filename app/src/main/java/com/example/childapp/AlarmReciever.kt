package com.example.childapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(arg0: Context, arg1: Intent) {
        Toast.makeText(arg0, "Alarm received!", Toast.LENGTH_LONG).show()
    }
}