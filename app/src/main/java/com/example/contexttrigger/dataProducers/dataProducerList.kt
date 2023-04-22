package com.example.contexttrigger.dataProducers


/***
 * ALL DATA PRODUCERS ADDED MUST BE INCLUDED HERE
 */


data class DataProducer (
    val instance: Class<*>,
    val isPendingIntent: Boolean,
    val publicName: String,
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

)