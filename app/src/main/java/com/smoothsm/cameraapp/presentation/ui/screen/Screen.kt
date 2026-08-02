package com.smoothsm.cameraapp.presentation.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Login : Screen()

    @Serializable
    data class Main(val userKey: String) : Screen()

    @Serializable
    data object FindPassword : Screen()

    @Serializable
    data object SignUp : Screen()

    @Serializable
    data object Camera : Screen()

    @Serializable
    data object Scanning : Screen()

    @Serializable
    data object ScanResult : Screen()

    @Serializable
    data object ResultEdit : Screen()

    @Serializable
    sealed class Bottom {
        @Serializable
        data object Ledger : Bottom()

        @Serializable
        data object Assets : Bottom()

        @Serializable
        data object Statistics : Bottom()

        @Serializable
        data object Profile : Bottom()

        enum class BottomTab(val route: Any, val title: String, val icon: ImageVector) {
            LEDGER(Ledger, "가계부", Icons.Rounded.CalendarMonth),
            ASSETS(Assets, "자산", Icons.Rounded.AccountBalanceWallet),
            STATISTICS(Statistics, "통계", Icons.Rounded.BarChart),
            PROFILE(Profile, "프로필", Icons.Rounded.AccountCircle),
        }
    }

    companion object {
        val bottomNavScreen = Bottom.BottomTab.entries
    }
    // TODO 파라미터를 받는 스크린일 경우 data class로 변경
}
