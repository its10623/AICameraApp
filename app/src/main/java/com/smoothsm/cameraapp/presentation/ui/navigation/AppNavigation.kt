package com.smoothsm.cameraapp.presentation.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.smoothsm.cameraapp.domain.model.Category
import com.smoothsm.cameraapp.domain.model.Receipt
import com.smoothsm.cameraapp.domain.model.ReceiptItem
import com.smoothsm.cameraapp.presentation.contract.ScanContract
import com.smoothsm.cameraapp.presentation.ui.screen.CameraScreen
import com.smoothsm.cameraapp.presentation.ui.screen.FindPasswordScreen
import com.smoothsm.cameraapp.presentation.ui.screen.SignInScreen
import com.smoothsm.cameraapp.presentation.ui.screen.MainScreen
import com.smoothsm.cameraapp.presentation.ui.screen.ResultEditScreen
import com.smoothsm.cameraapp.presentation.ui.screen.ScanResultScreen
import com.smoothsm.cameraapp.presentation.ui.screen.ScanningScreen
import com.smoothsm.cameraapp.presentation.ui.screen.Screen
import com.smoothsm.cameraapp.presentation.ui.screen.SignUpScreen
import com.smoothsm.cameraapp.presentation.viewmodel.SignInViewModel
import com.smoothsm.cameraapp.presentation.viewmodel.ScanViewModel
import com.smoothsm.cameraapp.presentation.viewmodel.SignUpViewModel
import java.time.LocalDateTime

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val scanViewModel: ScanViewModel = hiltViewModel()
    val signInViewModel: SignInViewModel = hiltViewModel()
    val signUpViewModel: SignUpViewModel = hiltViewModel()

    val receipt =
        Receipt(
            id = 1L,
            category = Category.GROCERY,
            storeName = "이마트 트레이더스",
            purchaseAt = "2026.06.17 19:24",
            totalPrice = 118000,
            items =
                listOf(
                    ReceiptItem(
                        id = 1L,
                        name = "올리브유",
                        price = 12900,
                        quantity = 1,
                    ),
                    ReceiptItem(
                        id = 2L,
                        name = "계란 30구",
                        price = 8900,
                        quantity = 2,
                    ),
                    ReceiptItem(
                        id = 3L,
                        name = "닭가슴살",
                        price = 15400,
                        quantity = 3,
                    ),
                    ReceiptItem(
                        id = 4L,
                        name = "두유 24입",
                        price = 19800,
                        quantity = 1,
                    ),
                    ReceiptItem(
                        id = 5L,
                        name = "화장지 30롤",
                        price = 23200,
                        quantity = 1,
                    ),
                    ReceiptItem(
                        id = 5L,
                        name = "화장지 30롤",
                        price = 23200,
                        quantity = 1,
                    ),
                    ReceiptItem(
                        id = 5L,
                        name = "화장지 30롤",
                        price = 23200,
                        quantity = 1,
                    ),
                    ReceiptItem(
                        id = 5L,
                        name = "화장지 30롤",
                        price = 23200,
                        quantity = 1,
                    ),
                    ReceiptItem(
                        id = 5L,
                        name = "화장지 30롤",
                        price = 23200,
                        quantity = 1,
                    ),
                ),
            createdAt = LocalDateTime.of(2026, 6, 17, 19, 25),
            updatedAt = LocalDateTime.of(2026, 6, 17, 19, 25),
        )

    NavHost(navController = navController, startDestination = Screen.Login) {
        composable<Screen.Login> {
            SignInScreen(
                viewModel = signInViewModel,
                onNavigateToMain = { userKey ->
                    val encodeUserKey = Uri.encode(userKey)
                    navController.navigate(Screen.Main(encodeUserKey)) {
                        popUpTo(Screen.Login) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onNavigateToFindPassword = { navController.navigate(Screen.FindPassword) },
                onNavigateToSignUp = { navController.navigate(Screen.SignUp) },
            )
        }
        composable<Screen.FindPassword> {
            FindPasswordScreen(
                onNavigateBack = { navController.popBackStack() },
            )
        }
        composable<Screen.SignUp> {
            SignUpScreen(
                viewModel = signUpViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToMain = { userKey ->
                    val encodeUserKey = Uri.encode(userKey)
                    navController.navigate(Screen.Main(encodeUserKey)) {
                        popUpTo(Screen.Login) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
            )
        }
        composable<Screen.Main> { backStackEntry ->
            val route = backStackEntry.toRoute<Screen.Main>()
            MainScreen(
                userKey = route.userKey,
                onNavigateToCamera = { navController.navigate(Screen.Camera) },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login) {
                        popUpTo(Screen.Login) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
            )
        }
        composable<Screen.Camera> {
            CameraScreen(
                onImageCaptured = { file ->
                    scanViewModel.handleIntent(ScanContract.Intent.StartScan(file))
                    navController.navigate(Screen.Scanning)
                },
            )
        }
        composable<Screen.Scanning> {
            ScanningScreen(viewModel = scanViewModel)
        }
        composable<Screen.ScanResult> {
            ScanResultScreen(receipt = receipt)
        }
        composable<Screen.ResultEdit> {
            ResultEditScreen(receipt = receipt)
        }
    }
}
