package com.rainbowt0506.philipplackner_multiplebackstackscompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rainbowt0506.philipplackner_multiplebackstackscompose.ui.screen.GenericScreen

@Composable
fun SettingsNavHost() {
    val settingsNavController = rememberNavController()

    NavHost(
        navController = settingsNavController,
        startDestination = "settings1"
    ) {
        for (i in 1..10) {
            composable("settings$i") {
                GenericScreen(
                    text = "Settings $i",
                    onNextClick = {
                        if (i < 10) {
                            settingsNavController.navigate("settings${i + 1}")
                        }
                    }
                )
            }
        }
    }
}