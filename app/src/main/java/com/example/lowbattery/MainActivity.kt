package com.example.lowbattery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataStoreManager = DataStoreManager(this)

        setContent {
            val navController = rememberNavController()
            val currentBatteryLevel by remember { mutableIntStateOf(100) }
            var threshold by remember { mutableIntStateOf(50) }
            var phoneNumber by remember { mutableStateOf("") }

            // Load saved settings
            LaunchedEffect(Unit) {
                threshold = dataStoreManager.batteryThreshold.first()
                phoneNumber = dataStoreManager.phoneNumber.first()
            }

            // Register the battery receiver
            MonitorBatteryReceiver(
                context = this,
                threshold = threshold,
                phoneNumber = phoneNumber
            )

            // Navigation setup
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(
                        currentThreshold = threshold,
                        currentPhoneNumber = phoneNumber,
                        currentBatteryLevel = currentBatteryLevel,
                        onNavigateToSettings = {
                            navController.navigate("settings")
                        }
                    )
                }
                composable("settings") {
                    SettingsScreen(
                        threshold = threshold,
                        phoneNumber = phoneNumber,
                        onSave = { newThreshold, newPhone ->
                            lifecycleScope.launch {
                                dataStoreManager.saveBatteryThreshold(newThreshold)
                                dataStoreManager.savePhoneNumber(newPhone)
                                threshold = newThreshold
                                phoneNumber = newPhone
                                navController.navigate("home")
                            }
                        }
                    )
                }
            }
        }
    }
}