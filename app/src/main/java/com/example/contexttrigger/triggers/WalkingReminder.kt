package com.example.contexttrigger.triggers

import android.content.Context
import android.util.Log
import com.example.contexttrigger.triggerManager.Trigger
import com.example.contexttrigger.dataProducers.TimeBasedReminder


class WalkingReminder : Trigger {

    private var emittersNeeded =  arrayOf("ALARM_DATA_PRODUCER")
    private var notificationTitle = "Walk More!"

    private lateinit var _context : Context


    override fun getDataProducerNeeded(): Array<String> {
        return emittersNeeded
    }

    override suspend fun shouldRunNotification(context: Context): Boolean {
        _context  = context
        return true
    }

    override suspend fun getNotificationMessage(context: Context): String {
        return "Oops!"
    }

    override suspend fun handle(context: Context, createdBy : String , data :String) {
        _context = context
        Log.d("dev-log:Alarm", "added")
    }



}