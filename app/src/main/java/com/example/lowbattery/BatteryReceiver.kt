package com.example.lowbattery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.util.Log
import android.Manifest
import androidx.core.content.ContextCompat

class BatteryReceiver(
    private val threshold: Int,
    private val phoneNumber: String
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val level = intent.getIntExtra(android.os.BatteryManager.EXTRA_LEVEL, -1)
        val dateTime = java.time.LocalDateTime.now().toString()

        if (level != -1 && level <= threshold) {
            val message = "Attention! Phone has low battery: $level%, at $dateTime"

            if (phoneNumber.isNotEmpty()) {
                // Check permission before sending the SMS
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    sendSms(context, phoneNumber, message)
                } else {
                    Log.e("BatteryReceiver", "SEND_SMS permission not granted")
                }
            } else {
                Log.e("BatteryReceiver", "Invalid phone number")
            }
        }
    }

    private fun sendSms(context: Context, phoneNumber: String, message: String) {
        try {
            val smsManager = context.getSystemService(SmsManager::class.java)
                ?: throw IllegalStateException("SmsManager is not available")
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Log.i("BatteryReceiver", "SMS sent to $phoneNumber")
        } catch (e: Exception) {
            Log.e("BatteryReceiver", "Failed to send SMS: ${e.message}")
        }
    }
}