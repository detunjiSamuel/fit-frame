package com.example.contexttrigger.components

import android.content.Context


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

    class Trigger(val context: Context)

    fun getEmitterNeeded() : Array<String> // who are you receiving data from

    suspend fun shouldRunNotification() : Boolean // should trigger notification

    suspend fun handle(createdBy : String, data : String) // Event_Handler
    // it is up to the developer to decide how to handle this

    fun getNotificationTitle() : String

    fun getNotificationMessage() : String

}