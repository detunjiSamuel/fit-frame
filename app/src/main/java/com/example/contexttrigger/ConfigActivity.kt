package com.example.contexttrigger


import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent

import com.example.contexttrigger.ui.theme.ContexttriggerTheme


import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.runtime.*



import android.util.Log

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

import com.example.contexttrigger.triggerManager.TriggerManager

import com.example.contexttrigger.ui.configurations.*


class ConfigActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("dev-log:framework-invoked", "Application started")

        /*
        *  THIS IS THE STARTUP OF THE TRIGGER MANAGER
        *
        *
        * */

        var triggerManger = TriggerManager()

        triggerManger.invoke(this)

        setContent {
            ContexttriggerTheme {
                createSetupScreen()

            }
        }
    }
}