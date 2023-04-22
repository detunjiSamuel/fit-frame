package com.example.contexttrigger.db.steps.setup

import com.example.contexttrigger.db.steps.Step


class StepsRepoImplementation(
    private val dao: StepsDao
) : StepsRepository {

    override suspend fun getStepByDate(date: String): Step? {
        return dao.getStepByDate(date)
    }

    override suspend fun insertSteps(step: Step) {
        return dao.insertStep(step)
    }

    override suspend fun count(date: String): Int {
        return dao.count(date)
    }

    override suspend fun update(step: Step) {
        return dao.update(step)
    }

    override suspend fun deleteStep(step: Step) {
        return dao.deleteStep(step)
    }
}
