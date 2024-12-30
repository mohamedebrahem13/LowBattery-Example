package com.example.lowbattery

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

@Composable
fun MonitorBatteryReceiver(
    context: Context,
    threshold: Int,
    phoneNumber: String
) {
    DisposableEffect(threshold, phoneNumber) {
        // Use applicationContext to avoid memory leaks related to Activity context
        val appContext = context.applicationContext
        val receiver = BatteryReceiver(threshold, phoneNumber)
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        appContext.registerReceiver(receiver, intentFilter)

        onDispose {
            appContext.unregisterReceiver(receiver)
        }
    }
}