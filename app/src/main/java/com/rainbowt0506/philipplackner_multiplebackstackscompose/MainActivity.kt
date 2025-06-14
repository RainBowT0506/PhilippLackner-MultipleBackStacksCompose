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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rainbowt0506.philipplackner_multiplebackstackscompose.model.BottomNavigationItem
import com.rainbowt0506.philipplackner_multiplebackstackscompose.navigation.ChatNavHost
import com.rainbowt0506.philipplackner_multiplebackstackscompose.navigation.HomeNavHost
import com.rainbowt0506.philipplackner_multiplebackstackscompose.navigation.MainGraph
import com.rainbowt0506.philipplackner_multiplebackstackscompose.navigation.SettingsNavHost
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

            var selectedItemIndex by rememberSaveable {
                mutableIntStateOf(0)
            }
            MultipleBackStacksComposeTheme {
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            items.forEachIndexed { index, item ->
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
                                        selectedItemIndex = index
                                        val screen = when (index) {
                                            0 -> MainGraph.Home
                                            1 -> MainGraph.Chat
                                            2 -> MainGraph.Settings
                                            else -> MainGraph.Home
                                        }

                                        navController.navigate(screen){
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
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
                        startDestination = MainGraph.Home,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<MainGraph.Home> { HomeNavHost() }
                        composable<MainGraph.Chat> { ChatNavHost() }
                        composable<MainGraph.Settings> { SettingsNavHost() }
                    }
                }
            }
        }
    }
}