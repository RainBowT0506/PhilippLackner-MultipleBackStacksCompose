package com.rainbowt0506.philipplackner_multiplebackstackscompose.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable

data class SettingItem(
    @DrawableRes val resId: Int,
    val title: String,
    val trailingContent: (@Composable () -> Unit)? = null,
    val onClick: () -> Unit
)
