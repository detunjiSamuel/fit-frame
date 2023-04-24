package com.example.contexttrigger.ui.configurations

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.contexttrigger.dataProducers.STEPS_RECORDING_PUBLIC_NAME
import com.example.contexttrigger.sensorManager.SensorController


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun createSetupScreen() {
    val keyboardController = LocalSoftwareKeyboardController.current
    val inputState = remember { mutableStateOf("") }


    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    val configHelper = ConfigHelper()

    var completedSteps by remember { mutableStateOf(0) }

    LaunchedEffect(true) {
        completedSteps = configHelper.getStepsCompleted(context)
    }



    val showNotification = remember { mutableStateOf(configHelper.getPreference(context, USER_ALLOWED_NOTIFICATION_KEY)) }
    val automaticStepRecoding = remember { mutableStateOf(configHelper.getPreference(context, AUTOMATIC_STEPS_RECORDING_KEY)) }

    val stepsGoal = remember {
        mutableStateOf(
            configHelper.getStepsGoal(context)
        )
    }

    fun toggleShowNotification () {
        configHelper.togglePreference(context, USER_ALLOWED_NOTIFICATION_KEY)
        showNotification.value = configHelper.getPreference(context, USER_ALLOWED_NOTIFICATION_KEY)

    }

    fun toggleStepRecording () {
        configHelper.togglePreference(context, AUTOMATIC_STEPS_RECORDING_KEY)
        automaticStepRecoding.value = configHelper.getPreference(context, AUTOMATIC_STEPS_RECORDING_KEY)

        // tell sensor controller

        if (automaticStepRecoding.value){

            val intent = Intent(context , SensorController::class.java)

            intent.putExtra("IS_REPORTING" , "PERMISSION_GRANTED" )

            intent.putExtra("DATA" , STEPS_RECORDING_PUBLIC_NAME )

            context.startService(intent)
        } else{

            val intent = Intent(context , SensorController::class.java)

            intent.putExtra("IS_REPORTING" , "PERMISSION_NOT_GRANTED"  )

            intent.putExtra("DATA" , STEPS_RECORDING_PUBLIC_NAME )

            context.startService(intent)

        }
    }



    Scaffold(

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .padding(bottom = 12.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = 3.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Set your daily steps goal here:",
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    StepsInputField(
                        modifier = Modifier.focusRequester(focusRequester),
                        value = inputState.value,
                        onValueChange = {
                            inputState.value = it
                        },
                        labelId = "Enter steps",
                        enabled = true,
                        isSingleLine = true,
                        onAction = KeyboardActions() {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            val steps = inputState.value.toIntOrNull()
                            if (steps != null) {
                                configHelper.setStepsGoal(context , steps)
                                stepsGoal.value = steps
                            } else {
                                Toast(context).setText("Please enter a valid number")
                                Log.d("invalid" ,  "enter-a-valid-number")
                            }
                        }
                    ) {
                        Text(text = "Set Goal")
                    }
                }
            }

            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .padding(bottom = 12.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = 3.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Configuration Options:",
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Show Notification", style = MaterialTheme.typography.body2)
                        Switch(
                            checked = showNotification.value ,
                            onCheckedChange = {
                                toggleShowNotification() }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Automatically Record Steps", style = MaterialTheme.typography.body2)
                        Switch(
                            checked = automaticStepRecoding.value,
                            onCheckedChange = { toggleStepRecording() }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            StepsGoal(goal = stepsGoal.value, completed = completedSteps)
        }
    }
}

