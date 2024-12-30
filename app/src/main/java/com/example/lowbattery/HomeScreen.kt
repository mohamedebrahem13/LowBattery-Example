package com.example.lowbattery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    currentThreshold: Int,
    currentPhoneNumber: String,
    currentBatteryLevel: Int,
    onNavigateToSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Battery Monitor",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text("Current Battery Level: $currentBatteryLevel%")
        Text("Battery Threshold: $currentThreshold%")
        Text("Phone Number: $currentPhoneNumber")

        Button(
            onClick = { onNavigateToSettings() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Settings")
        }
    }
}
