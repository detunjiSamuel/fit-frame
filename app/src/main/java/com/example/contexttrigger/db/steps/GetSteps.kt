package com.example.contexttrigger.db.steps

import android.content.Context
import com.example.contexttrigger.db.steps.setup.StepsDatabase
import com.example.contexttrigger.db.steps.setup.StepsRepoImplementation

class GetSteps {

    suspend operator fun invoke(context : Context, date: String): Step? {
        val stepsDao = StepsDatabase.getInstance(context).stepsDao
        val stepsRepository = StepsRepoImplementation(stepsDao)
        return stepsRepository.getStepByDate(date)


    }
}