package com.example.contexttrigger.emitters


/***
 * ALL CONTEXT LISTENERS ADDED MUST BE INCLUDED HERE
 */


data class ContextListener (
    val instance: Class<*>,
    val isPendingIntent: Boolean,
    val publicName: String,
)

var contextListenersList = arrayOf<ContextListener>(
    ContextListener(
        StepsActivityRecording::class.java,
         false,
        STEPS_RECORDING_PUBLIC_NAME
    )
)