package com.example.contexttrigger

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.contexttrigger.ui.theme.ContexttriggerTheme


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.contexttrigger.components.TriggerManager


class ConfigActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("dev-log:framework-invoked", "Application started")

        var triggerMangerTest = TriggerManager()

        triggerMangerTest.invoke(this)

        setContent {
            ContexttriggerTheme {
                createSetupScreen()

            }
        }
    }
}



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun createSetupScreen() {
    val keyboardController = LocalSoftwareKeyboardController.current
    val inputState = remember { mutableStateOf("") }


    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current



    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Configurations") }) },
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
                                setStepsGoal(steps)
                            } else {
//                            Toast().setText("Please enter a valid number")
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
                            checked = true,
                            onCheckedChange = { /* Handle show notification switch */ }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Automatically Record Steps", style = MaterialTheme.typography.body2)
                        Switch(
                            checked = true,
                            onCheckedChange = { /* Handle auto-record steps switch */ }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private fun setStepsGoal(steps: Int) {
//    val intent = Intent(context, ContextUpdateManager::class.java)
//    intent.putExtra("Data", "Goal")
//    intent.putExtra("Steps", steps)
//    context.startService(intent)
}

@Composable
fun StepsInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        label = { Text(text = labelId) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Walking Icon"
            )
        },
        singleLine = isSingleLine,
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = MaterialTheme.colors.onBackground
        ),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction
    )
}