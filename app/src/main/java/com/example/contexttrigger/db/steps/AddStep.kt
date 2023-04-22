package com.example.contexttrigger.db.steps

import android.content.Context
import com.example.contexttrigger.db.steps.setup.StepsDatabase
import com.example.contexttrigger.db.steps.setup.StepsRepoImplementation

class AddStep {

    suspend operator fun invoke(context: Context, date : String, steps : Int )
    {
        val stepsDao = StepsDatabase.getInstance(context).stepsDao
        val stepsRepository = StepsRepoImplementation(stepsDao)

        var isValid = stepsRepository.getStepByDate(date)

        if (isValid != null)
        {

            isValid.steps = isValid.steps + steps
            isValid.updatedAt = System.currentTimeMillis()

            stepsRepository.update(isValid)
        }
        else{

            var newSteps = Step(
                date,
                steps
            )

            stepsRepository.insertSteps(newSteps)

        }

    }
}