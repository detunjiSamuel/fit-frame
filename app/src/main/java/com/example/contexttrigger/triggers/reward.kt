package com.example.contexttrigger.triggers

import RewardHelper
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.contexttrigger.SHARED_PREFERENCES_NAME
import com.example.contexttrigger.dataProducers.LOCATION_RECORDING_PUBLIC_NAME
import com.example.contexttrigger.dataProducers.STEPS_RECORDING_PUBLIC_NAME
import com.example.contexttrigger.db.steps.GetSteps
import com.example.contexttrigger.helpers.LocationHelper
import com.example.contexttrigger.helpers.TimeHelper
import com.example.contexttrigger.triggerManager.Trigger


var REWARD_KEY = "DISCOUNT-FOR-USER"

var API_KEY = ""

var possibleReward = arrayOf("spa" , "restaurant" ,"gym")



class reward : Trigger {
    private var emitterNeeded =  arrayOf(LOCATION_RECORDING_PUBLIC_NAME , STEPS_RECORDING_PUBLIC_NAME)

    private lateinit var _context : Context

    override fun getDataProducerNeeded(): Array<String> {
        return emitterNeeded

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun shouldRunNotification(context: Context): Boolean {

        _context =  context


       var goalCompleted = getStepsCompleted() >= getStepGoal()

        var hasReward = getReward() != ""


        return hasReward && goalCompleted

    }

    override suspend fun handle(context: Context, createdBy: String, data: String) {

        if (createdBy == LOCATION_RECORDING_PUBLIC_NAME)
        {
            var foundReward =  false

            var location = LocationHelper().stringToLocation(data)

            var rewardHelper = RewardHelper(context , API_KEY )



            for (place in possibleReward)
            {
                if (!foundReward)
                {
                    rewardHelper.getRewardNearBy(place ,
                        location.latitude,
                        location.longitude,
                        1000
                        ) {

                        message ->
                        if (message != null)

                        {
                            if (message.startsWith("Sorry"))
                                Log.e("dev-log:NO REWARD" , message)

                            else if (message.startsWith("Error"))
                                Log.e("dev-log:REWARD FAILED" , message)

                            else
                            {
                                Log.e("dev-log:REWARD" , message)

                                storeReward(message)

                                foundReward = true

                            }
                        }

                    }
                }
            }
        }

    }

    override suspend fun getNotificationTitle(context: Context): String {

        return "completed reward"
    }

    override suspend fun getNotificationMessage(context: Context): String {

        return getReward()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getStepsCompleted(): Int {

        val completedSteps = GetSteps()(_context , TimeHelper().currentDate()) ?: return 0

        return completedSteps.steps

    }

    private suspend fun getStepGoal(): Int {

        val sharedPref =
            _context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        return sharedPref.getInt(STEPS_GOAL_KEY, defaultGoal)

    }

    private suspend fun getReward(): String {

        val sharedPref =
            _context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        var reward = sharedPref.getString(REWARD_KEY , "EMPTY")

        if ( reward == null || reward == "EMPTY")
            return ""

        return reward

    }


    private fun storeReward(reward :String)  {

        Log.d("dev-log:ConfigHelper", "Reward Goal:")
        val sharedPref = _context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString(REWARD_KEY, reward)
            apply()
        }

    }


}