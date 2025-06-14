package com.rainbowt0506.philipplackner_multiplebackstackscompose.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class MainGraph() {
    @Serializable object Home : MainGraph()
    @Serializable object Chat : MainGraph()
    @Serializable object Settings : MainGraph()
    @Serializable object SettingScreen : MainGraph()
}