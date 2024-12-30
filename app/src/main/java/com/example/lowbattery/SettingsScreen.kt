package com.example.lowbattery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    threshold: Int,
    phoneNumber: String,
    onSave: (Int, String) -> Unit
) {
    var batteryThreshold by remember { mutableStateOf(threshold.toString()) }
    var phoneNumberInput by remember { mutableStateOf(phoneNumber) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = batteryThreshold,
            onValueChange = { batteryThreshold = it },
            label = { Text("Battery Threshold (%)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        TextField(
            value = phoneNumberInput,
            onValueChange = { phoneNumberInput = it },
            label = { Text("Phone Number") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
        )

        Button(onClick = {
            val thresholdValue = batteryThreshold.toIntOrNull() ?: 0
            onSave(thresholdValue, phoneNumberInput)
        }) {
            Text("Save")
        }
    }
}
