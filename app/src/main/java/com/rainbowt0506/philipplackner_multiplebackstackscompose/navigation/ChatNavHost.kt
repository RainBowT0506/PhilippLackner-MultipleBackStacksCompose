package com.rainbowt0506.philipplackner_multiplebackstackscompose.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rainbowt0506.philipplackner_multiplebackstackscompose.ui.screen.GenericScreen

@Composable
fun ChatNavHost(modifier: Modifier = Modifier) {
    val chatNavController = rememberNavController()

    NavHost(
        navController = chatNavController,
        startDestination = "chat1",
        modifier = modifier
    ) {
        for (i in 1..10) {
            composable("chat$i") {
                GenericScreen(
                    text = "Chat $i",
                    onNextClick = {
                        if (i < 10) {
                            chatNavController.navigate("chat${i + 1}")
                        }
                    }
                )
            }
        }
    }
}