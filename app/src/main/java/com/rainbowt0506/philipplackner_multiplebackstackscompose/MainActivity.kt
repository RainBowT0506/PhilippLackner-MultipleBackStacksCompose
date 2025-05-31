package com.rainbowt0506.philipplackner_multiplebackstackscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rainbowt0506.philipplackner_multiplebackstackscompose.model.BottomNavigationItem
import com.rainbowt0506.philipplackner_multiplebackstackscompose.ui.screen.GenericScreen
import com.rainbowt0506.philipplackner_multiplebackstackscompose.ui.theme.MultipleBackStacksComposeTheme

val items = listOf(
    BottomNavigationItem(
        title = "Home",
        route = "home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavigationItem(
        title = "Chat",
        route = "chat",
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email
    ),
    BottomNavigationItem(
        title = "Settings",
        route = "settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            MultipleBackStacksComposeTheme {
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            items.forEach { item ->
                                val isSelected = item.route == currentRoute
                                NavigationBarItem(
                                    selected = isSelected,
                                    label = { Text(item.title) },
                                    icon = {
                                        Icon(
                                            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                            contentDescription = item.title
                                        )
                                    },
                                    onClick = {
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = false
                                            }
                                            launchSingleTop = true
                                            restoreState = false
                                        }
                                    }
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // 頂層起始頁
                        composable("home") {
                            GenericScreen("Home 1") { navController.navigate("home2") }
                        }
                        composable("chat") {
                            GenericScreen("Chat 1") { navController.navigate("chat2") }
                        }
                        composable("settings") {
                            GenericScreen("Settings 1") { navController.navigate("settings2") }
                        }

                        // 多層頁面（Home2 ~ Home10, Chat2 ~ Chat10, Settings2 ~ Settings10）
                        for (i in 2..10) {
                            composable("home$i") {
                                GenericScreen("Home $i") {
                                    if (i < 10) navController.navigate("home${i + 1}")
                                }
                            }
                            composable("chat$i") {
                                GenericScreen("Chat $i") {
                                    if (i < 10) navController.navigate("chat${i + 1}")
                                }
                            }
                            composable("settings$i") {
                                GenericScreen("Settings $i") {
                                    if (i < 10) navController.navigate("settings${i + 1}")
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}