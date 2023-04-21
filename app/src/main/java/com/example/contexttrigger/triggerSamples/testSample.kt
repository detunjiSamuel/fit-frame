package com.example.contexttrigger.triggerSamples

import android.content.Context
import com.example.contexttrigger.components.Trigger


/***
 * IGNORE I JUST NEEDED IT FOR TEST
 */

class testSample : Trigger {
    override fun getEmitterNeeded(): Array<String> {
        TODO("Not yet implemented")
    }

    override suspend fun shouldRunNotification(context: Context): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun handle(context: Context, createdBy: String, data: String) {
        TODO("Not yet implemented")
    }

    override fun getNotificationTitle(): String {
        TODO("Not yet implemented")
    }

    override fun getNotificationMessage(): String {
        TODO("Not yet implemented")
    }
}