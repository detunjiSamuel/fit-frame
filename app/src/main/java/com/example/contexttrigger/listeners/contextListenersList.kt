package com.example.contexttrigger.listeners


/***
 * ALL LISTENERS ADDED MUST BE INCLUDED HERE
 */

//interface IContextListener {
//    val instance: Class<*>
//    val isPendingIntent: Boolean
//    val publicName: String
//}

data class ContextListener (
    val instance: Class<*>,
    val isPendingIntent: Boolean,
    val publicName: String,
)

var contextListenersList = arrayOf<ContextListener>(
    ContextListener(
         Steps::class.java,
         false,
        "STEPS_ACTIVITY_RECORDING"
    )
)




//var contextListenersList = arrayOf(
//    mapOf(
//        "instance" to Steps::class.java,
//        "isPendingIntent" to false,
//        "publicName" to "STEPS_ACTIVITY_RECORDING"
//    )
//)



//var contextListenersList = arrayOf(
//    mapOf(
//        "instance" to Steps::class.java,
//        "isPendingIntent" to false,
//        "publicName" to "STEPS_ACTIVITY_RECORDING"
//    )
//)