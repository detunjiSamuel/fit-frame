package com.example.contexttrigger.db.steps.setup

import androidx.room.*
import com.example.contexttrigger.db.steps.Step

@Dao
interface StepsDao {

    @Query("SELECT * FROM steps WHERE date = :date")
    suspend fun getStepByDate(date: String): Step?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStep(step: Step)

    @Query("SELECT COUNT() FROM steps WHERE date = :date")
    suspend fun count(date: String): Int

    @Update
    suspend fun update(step: Step)


    @Delete
    suspend fun deleteStep(step: Step)
}