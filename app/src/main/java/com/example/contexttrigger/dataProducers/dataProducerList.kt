package com.example.contexttrigger.dataProducers


/***
 * ALL DATA PRODUCERS ADDED MUST BE INCLUDED HERE
 */


data class DataProducer (
    val instance: Class<*>,
    val isPendingIntent: Boolean,
    val publicName: String,
    val isEssential : Boolean = false
)

var dataProducerList = arrayOf<DataProducer>(
    DataProducer(
        StepsActivityRecording::class.java,
         false,
        STEPS_RECORDING_PUBLIC_NAME
    ),
    DataProducer(
        LocationRecording :: class.java,
        true,
        LOCATION_RECORDING_PUBLIC_NAME
    ),

    DataProducer(
        WeatherRecording :: class.java,
        true,
        WEATHER_RECORDING_PUBLIC_NAME
    )
    ,

    DataProducer(
        AirplaneModeRecording :: class.java,
        true,
        AIRPLANE_RECORDING_PUBLIC_NAME,
        true
    ),

    DataProducer(
        InternetConnectivityRecording :: class.java,
        true,
        INTERNET_RECORDING_PUBLIC_NAME,
        true
    ),

    DataProducer(
        TimeBasedReminder :: class.java,
        true,
        "ALARM_DATA_PRODUCER",
        true
    )



)