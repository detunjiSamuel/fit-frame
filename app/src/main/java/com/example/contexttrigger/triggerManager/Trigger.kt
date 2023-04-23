package com.example.contexttrigger.triggerManager

import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.contexttrigger.R


 var NOT_IMPLEMENTED_TRIGGER_NOTIFICATION_MESSAGE = "YOU_DID_NOT_DEFINE"



interface Trigger {
    // individual Trigger
    // BASE LOGIC THAT WILL BE USED
    /* Figuring out as i type
    * Mention sensor needed -> not to sure yet
    *   Sensor is controlled by another class
    * Mention State needed -> not to sure yet
    * Point to file that should extend
    *           should_run() -> boolean
    *           Handle() -> returns Notification/message sent i.e string
    *
    * This passes control to something else
    *
    * name , handle_event , should_run , handle
    *
    * next : have a demo to test all out
    *
    * listen for changes
    *
    * */


    fun getDataProducerNeeded() : Array<String> // who are you receiving data from

    suspend fun shouldRunNotification(context: Context) : Boolean // should trigger notification

    suspend fun handle( context:Context , createdBy : String, data : String) // Event_Handler
    // it is up to the developer to decide how to handle this

    fun getNotificationTitle() : String = NOT_IMPLEMENTED_TRIGGER_NOTIFICATION_MESSAGE

    fun getNotificationMessage(context: Context) : String


    // Support custom notification
    fun getCustomNotification(context : Context): NotificationCompat.Builder = NotificationCompat.Builder(context,
        NOT_IMPLEMENTED_TRIGGER_NOTIFICATION_MESSAGE )
        .setContentTitle(NOT_IMPLEMENTED_TRIGGER_NOTIFICATION_MESSAGE)
        .setContentText(NOT_IMPLEMENTED_TRIGGER_NOTIFICATION_MESSAGE)
        .setSmallIcon(R.drawable.ic_launcher_foreground)

}