package com.rainbowt0506.philipplackner_multiplebackstackscompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rainbowt0506.philipplackner_multiplebackstackscompose.ui.screen.GenericScreen

@Composable
fun HomeNavHost(modifier: Modifier = Modifier) {
    val homeNavController = rememberNavController()

    NavHost(
        homeNavController,
        startDestination = "home1",
        modifier = modifier
    ) {
        for (i in 1..10) {
            composable("home$i") {
                GenericScreen(
                    text = "Home $i",
                    onNextClick = {
                        if (i < 10) {
                            homeNavController.navigate("home${i + 1}")
                        }
                    }
                )
            }
        }
    }
}
