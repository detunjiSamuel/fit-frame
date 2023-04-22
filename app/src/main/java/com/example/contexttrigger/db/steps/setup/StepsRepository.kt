package com.example.contexttrigger.db.steps.setup

import com.example.contexttrigger.db.steps.Step


interface StepsRepository {

    suspend fun getStepByDate(date: String): Step?

    suspend fun insertSteps(step: Step)

    suspend fun count(date: String): Int

    suspend fun update(step: Step)

    suspend fun deleteStep(step: Step)
}