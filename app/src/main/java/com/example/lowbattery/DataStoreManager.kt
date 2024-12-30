package com.example.lowbattery

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class DataStoreManager(context: Context) {
    private val dataStore = context.applicationContext.dataStore

    companion object {
        val BATTERY_THRESHOLD_KEY = intPreferencesKey("battery_threshold")
        val PHONE_NUMBER_KEY = stringPreferencesKey("phone_number")
        val SMS_SENT_FLAG_KEY = booleanPreferencesKey("sms_sent_flag")
    }

    // Retrieve values
    val batteryThreshold: Flow<Int> = dataStore.data.map { preferences ->
        preferences[BATTERY_THRESHOLD_KEY] ?: 50
    }

    val phoneNumber: Flow<String> = dataStore.data.map { preferences ->
        preferences[PHONE_NUMBER_KEY] ?: ""
    }

    // Save values
    suspend fun saveBatteryThreshold(threshold: Int) {
        dataStore.edit { preferences ->
            preferences[BATTERY_THRESHOLD_KEY] = threshold
        }
    }

    suspend fun savePhoneNumber(phoneNumber: String) {
        dataStore.edit { preferences ->
            preferences[PHONE_NUMBER_KEY] = phoneNumber
        }
    }

    // Get SMS Sent Flag
    suspend fun getSmsSentFlag(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[SMS_SENT_FLAG_KEY] ?: false
    }

    // Set SMS Sent Flag
    suspend fun setSmsSentFlag(sent: Boolean) {
        dataStore.edit { preferences ->
            preferences[SMS_SENT_FLAG_KEY] = sent
        }
    }
}