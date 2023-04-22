package com.example.contexttrigger.db.steps.setup


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contexttrigger.db.steps.Step


@Database(entities = [Step::class], version = 3, exportSchema = false)
abstract class StepsDatabase: RoomDatabase() {

    abstract val stepsDao: StepsDao

    companion object {
        const val DATABASE_NAME = "steps"

        @Volatile
        var INSTANCE: StepsDatabase? = null

        fun getInstance(context: Context): StepsDatabase {
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
                }
                return instance
            }
        }
    }
}