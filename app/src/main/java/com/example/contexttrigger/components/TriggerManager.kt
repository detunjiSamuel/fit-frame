package com.example.contexttrigger.components



class TriggerManager {
    // Main Interface of for user to access/import and use it
    private val triggers = mutableListOf<Trigger>()

    fun registerTrigger(trigger: Trigger) {
        triggers.add(trigger)
    }

    fun getAllTriggers(): Array<Trigger> {
        return triggers.toTypedArray()
    }

    fun invoke() {
        // this will ask for permissions based on triggers registered
    }

    fun cleanup(){
        // tasks that have to stop
    }

}