package com.example.contexttrigger.components

import com.example.contexttrigger.components.Trigger

class TriggerManager {
    // Main Interface of for user to access
    private val triggers = mutableListOf<Trigger>()

    fun registerTrigger(trigger: Trigger) {
        triggers.add(trigger)
    }

    fun getAllTriggers(): Array<Trigger> {
        return triggers.toTypedArray()
    }

}