package com.example.contexttrigger.triggerManager

import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.contexttrigger.R


 var NOT_IMPLEMENTED_TRIGGER_NOTIFICATION_MESSAGE = "YOU_DID_NOT_DEFINE"



interface Trigger {


    fun getDataProducerNeeded() : Array<String> // who are you receiving data from

    suspend fun shouldRunNotification(context: Context) : Boolean // should trigger notification

    suspend fun handle( context:Context , createdBy : String, data : String) // Event_Handler
    // it is up to the developer to decide how to handle this

    suspend fun getNotificationTitle(context: Context) : String = NOT_IMPLEMENTED_TRIGGER_NOTIFICATION_MESSAGE

    suspend fun getNotificationMessage(context: Context) : String


    // Support custom notification
    fun getCustomNotification(context : Context): NotificationCompat.Builder = NotificationCompat.Builder(context,
        NOT_IMPLEMENTED_TRIGGER_NOTIFICATION_MESSAGE )
        .setContentTitle(NOT_IMPLEMENTED_TRIGGER_NOTIFICATION_MESSAGE)
        .setContentText(NOT_IMPLEMENTED_TRIGGER_NOTIFICATION_MESSAGE)
        .setSmallIcon(R.drawable.ic_launcher_foreground)

}