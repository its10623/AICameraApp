package com.smoothsm.cameraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.smoothsm.cameraapp.domain.model.Category
import com.smoothsm.cameraapp.domain.model.Receipt
import com.smoothsm.cameraapp.domain.model.ReceiptItem
import com.smoothsm.cameraapp.presentation.ui.navigation.AppNavigation
import com.smoothsm.cameraapp.presentation.ui.screen.ResultEditScreen
import com.smoothsm.cameraapp.presentation.ui.screen.SignInScreen
import com.smoothsm.cameraapp.presentation.ui.screen.SignUpScreen
import com.smoothsm.cameraapp.presentation.ui.theme.CameraAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle =
                SystemBarStyle.light(
                    scrim = android.graphics.Color.TRANSPARENT,
                    darkScrim = android.graphics.Color.TRANSPARENT,
                ),
            navigationBarStyle =
                SystemBarStyle.light(
                    scrim = android.graphics.Color.WHITE,
                    darkScrim = android.graphics.Color.WHITE,
                ),
        )
        setContent {
            CameraAppTheme {
                AppNavigation()
                /*ResultEditScreen(
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
                                    id = 6L,
                                    name = "화장지 30롤",
                                    price = 23200,
                                    quantity = 1,
                                ),
                                ReceiptItem(
                                    id = 7L,
                                    name = "화장지 30롤",
                                    price = 23200,
                                    quantity = 1,
                                ),
                                ReceiptItem(
                                    id = 8L,
                                    name = "화장지 30롤",
                                    price = 23200,
                                    quantity = 1,
                                ),
                                ReceiptItem(
                                    id = 9L,
                                    name = "화장지 30롤",
                                    price = 23200,
                                    quantity = 1,
                                ),
                            ),
                        createdAt = LocalDateTime.of(2026, 6, 17, 19, 25),
                        updatedAt = LocalDateTime.of(2026, 6, 17, 19, 25),
                    ),
                )*/
            }
        }
    }
}
