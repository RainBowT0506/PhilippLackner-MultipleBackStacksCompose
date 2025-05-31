# 範例程式碼 - Single Back Stack
## 在 build.gradle(:app) 中設定 Compose 與 Navigation 依賴
```
dependencies {
    implementation "androidx.navigation:navigation-compose:2.7.7"
    implementation "androidx.compose.material3:material3:1.2.0"
    implementation "androidx.compose.material:material-icons-extended"
}
```
## 在 `data/model` 建立 BottomNavigationItem 資料類別
```
data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
```
## 設計底部導覽欄
```
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
                }
            }
        }
    }
}
```
## 設定 Navigation Graph 並
```
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
```

## 在 `ui/screen` 建立可重複使用的 GenericScreen

```
@Composable
fun GenericScreen(
    text: String,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNextClick) {
            Text("Next")
        }
    }
}
```

# 將 Single Back Stack 重構為 Multiple Back Stacks
## 使用獨立堆疊提取 NavHost 並且BottomNavigation 保留每個 Tab 的獨立 Back Stack

```kotlin
@Composable
fun HomeNavHost() {
    val homeNavController = rememberNavController()
    NavHost(homeNavController, startDestination = "home1") {
        for (i in 1..10) {
            composable("home$i") {
                GenericScreen("Home $i") {
                    if (i < 10) homeNavController.navigate("home${i + 1}")
                }
            }
        }
    }
}
```

---

```kotlin
@Composable
fun ChatNavHost() {
    val chatNavController = rememberNavController()
    NavHost(chatNavController, startDestination = "chat1") {
        for (i in 1..10) {
            composable("chat$i") {
                GenericScreen("Chat $i") {
                    if (i < 10) chatNavController.navigate("chat${i + 1}")
                }
            }
        }
    }
}
```

---

```kotlin
@Composable
fun SettingsNavHost() {
    val settingsNavController = rememberNavController()
    NavHost(settingsNavController, startDestination = "settings1") {
        for (i in 1..10) {
            composable("settings$i") {
                GenericScreen("Settings $i") {
                    if (i < 10) settingsNavController.navigate("settings${i + 1}")
                }
            }
        }
    }
}
```

---
```kotlin
NavHost(rootNavController, startDestination = "home") {
    composable("home") { HomeNavHost() }
    composable("chat") { ChatNavHost() }
    composable("settings") { SettingsNavHost() }
}
```



---
# Compose Navigation 重構指南：從 Single Back Stack 到 Multiple Back Stacks

在使用 Jetpack Compose 開發多頁面的應用時，底部導覽（Bottom Navigation）常常扮演重要角色。最初我們通常會採用單一導航堆疊（Single Back Stack）實作，但很快就會遇到 UX 的瓶頸：使用者切換 Tab 時無法保留每個分頁的歷史狀態。

這篇文章將從開發者的角度，完整拆解如何一步步將 App 架構從 Single Back Stack 重構為 Multiple Back Stacks，提升導航體驗與程式彈性。

---

## 問題背景：Single Back Stack 的限制

當所有頁面共用一個 NavController：

* 所有 Tab 共用一條導航堆疊。
* 使用者在 Tab 中深入點擊幾層後，切換到其他 Tab，然後再切回來，畫面會重置為初始頁面。
* 無法維持使用者在各個 Tab 中的歷史狀態。

這對使用者來說體驗很差，對開發者來說也限制了彈性。

---

## 重構目標：Multiple Back Stacks

所謂 Multiple Back Stacks，是指：

* 每個底部導覽的 Tab 都有獨立的 NavController 和導航堆疊。
* 使用者在某個 Tab 中的瀏覽紀錄可以被完整保留。
* 切換 Tab 僅切換視圖，不清除對應的狀態。

---

## 重構步驟一覽

### 抽離每個 Tab 的導覽圖（Navigation Graph）

將每個主要 Tab 的頁面定義，從主導航圖中獨立出來，轉為一個個獨立的 Navigation Host（NavHost），每個都有自己的 NavController。

這樣每個 Tab 的頁面狀態就能獨立管理，互不干擾。

---

### 建立中央控制器（Root NavController）來切換 Tab

雖然每個 Tab 有獨立的導覽堆疊，但仍需要一個中央入口點（通常是主畫面或 MainActivity），用來根據當前選擇的 Tab 顯示對應的 NavHost。

這裡的 Root NavController 不負責管理子頁面，只負責控制當前顯示哪個 Tab。

---

### 使用 rememberSaveable 記錄當前 Tab

為了讓組件重組時保留目前選中的 Tab，你應該用 `rememberSaveable` 來儲存 Tab 狀態，避免畫面在重繪時跳回預設值。

---

### 整合到底部導覽（Bottom Navigation）中

在 `BottomNavigationBar` 的點擊事件中，觸發切換當前 Tab 的狀態，並讓畫面顯示對應的 NavHost。

記住，切換的是哪個 NavHost 顯示，而不是在主導航圖中切換 route。

---

### 保持每個 NavHost 的狀態不被重置

在 Compose 中，每次組件被重新組合（Recompose）時，如果 NavController 沒有適當地用 `remember` 管理，就會導致狀態丟失。

確保每個 NavController 是 `remember` 出來的，並包在記憶範圍內（通常建議不要放在可重組的函式內部直接 new）。

---

## 整體架構小結

| 組件                       | 職責                     |
| ------------------------ | ---------------------- |
| RootNavController        | 管理目前顯示哪個 Tab           |
| 個別 NavController（每個 Tab） | 管理各自的導航狀態與堆疊           |
| BottomNavigation         | 操作目前 Tab，觸發切換          |
| rememberSaveable         | 儲存目前選中的 Tab 狀態         |
| 每個 NavHost               | 建立專屬的 Navigation Graph |

---

## 常見錯誤與注意事項

* 誤用單一 NavController：這會讓你永遠無法保留 Tab 狀態。
* 沒有使用 rememberSaveable：組件重組時 Tab 狀態會丟失。
* NavController 沒有隔離範圍：每次切換都重建 NavHost，造成 UI 閃爍或導航重置。

---

## 結語

Single Back Stack 適合簡單導覽結構，但一旦牽涉到底部導覽、多層畫面與複雜互動，採用 Multiple Back Stacks 幾乎是不可避免的選擇。

透過這個重構流程，你可以：

* 提升使用者體驗
* 增加程式可維護性
* 與原生 Android 導覽行為保持一致

---

