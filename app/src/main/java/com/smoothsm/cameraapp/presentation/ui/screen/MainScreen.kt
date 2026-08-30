package com.smoothsm.cameraapp.presentation.ui.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.smoothsm.cameraapp.presentation.contract.SettingsContract
import com.smoothsm.cameraapp.presentation.ui.component.BottomNavigationBar
import com.smoothsm.cameraapp.presentation.ui.component.CameraFab
import com.smoothsm.cameraapp.presentation.ui.component.Dialog
import com.smoothsm.cameraapp.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    userKey: String,
    onNavigateToCamera: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

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
        snackbarHost = { SnackbarHost(snackBarHostState) },
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
                val settingsViewModel: SettingsViewModel = hiltViewModel()
                val state by settingsViewModel.uiState.collectAsStateWithLifecycle()

                LaunchedEffect(Unit) {
                    settingsViewModel.uiSideEffect.collect { effect ->
                        when (effect) {
                            is SettingsContract.SideEffect.NavigateToLogin -> onNavigateToLogin()
                            is SettingsContract.SideEffect.ShowError -> {
                                coroutineScope.launch { snackBarHostState.showSnackbar(effect.message) }
                            }
                        }
                    }
                }

                SettingsScreen(
                    userName = state.userName,
                    userEmail = state.userEmail,
                    onLogout = { settingsViewModel.handleIntent(SettingsContract.Intent.Logout) },
                    onDeleteAccount = { settingsViewModel.handleIntent(SettingsContract.Intent.DeleteAccount) },
                )
            }
        }
    }
}
