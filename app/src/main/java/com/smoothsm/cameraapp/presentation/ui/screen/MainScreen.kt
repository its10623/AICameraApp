package com.smoothsm.cameraapp.presentation.ui.screen

import android.app.Activity
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.smoothsm.cameraapp.presentation.ui.component.BottomNavigationBar
import com.smoothsm.cameraapp.presentation.ui.component.CameraFab
import com.smoothsm.cameraapp.presentation.ui.component.Dialog

@Composable
fun MainScreen(
    userKey: String,
    onNavigateToCamera: () -> Unit,
) {
    val context = LocalContext.current

    val bottomNavController = rememberNavController()
    val currentBackStack by bottomNavController.currentBackStackEntryAsState()

    val currentDestination = currentBackStack?.destination

    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = currentDestination?.hasRoute<Screen.Bottom.Ledger>() == true) {
        showExitDialog = true
    }

    if (showExitDialog) {
        Dialog(
            title = "앱을 종료하시겠습니까?",
            isTextField = false,
            onDismiss = { showExitDialog = false },
            onConfirm = {
                showExitDialog = false
                (context as Activity).finish()
            },
        )
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentDestination = currentDestination,
                onNavigate = { route ->
                    if (currentDestination?.hasRoute(route::class) == true) return@BottomNavigationBar
                    bottomNavController.navigate(route) {
                        // 상태저장되어 다른탭 갔다와도 상태가 바뀌지 않음
                        popUpTo(bottomNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        },
        floatingActionButton = {
            val isBottomTabScreen =
                Screen.bottomNavScreen.any { tab ->
                    currentDestination?.hasRoute(tab.route::class) == true
                }
            if (isBottomTabScreen) {
                CameraFab(
                    modifier =
                        Modifier
                            .offset(y = 28.dp),
                    onCamera = {
                        onNavigateToCamera()
                    },
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        modifier =
            Modifier
                .fillMaxSize(),
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = Screen.Bottom.Ledger,
            modifier = Modifier.padding(paddingValues),
        ) {
            composable<Screen.Bottom.Ledger> {
                LedgerScreen()
            }
            composable<Screen.Bottom.Assets> {
                // AssetsScreen()
            }
            composable<Screen.Bottom.Statistics> {
                // StatisticsScreen()
            }
            composable<Screen.Bottom.Profile> {
                SettingsScreen()
            }
        }
    }
}
