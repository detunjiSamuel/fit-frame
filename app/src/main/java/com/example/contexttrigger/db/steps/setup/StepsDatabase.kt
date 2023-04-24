package com.example.contexttrigger.db.steps.setup


import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contexttrigger.db.steps.Step


@Database(entities = [Step::class], version = 1, exportSchema = false)
abstract class StepsDatabase: RoomDatabase() {

    abstract fun stepsDao(): StepsDao

    companion object {
        const val DATABASE_NAME = "steps"

        @Volatile
        var INSTANCE: StepsDatabase? = null

        fun getInstance(context: Context): StepsDatabase {
            Log.d("dev-log:Steps db", "getting instance")
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StepsDatabase::class.java,
                        DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance

                    Log.d("dev-log:Steps db", "found instance")

                } else {
                    Log.d("dev-log:Steps db", "error from here")
                }
                return instance
            }
        }
    }
}