package com.smoothsm.cameraapp.presentation.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.smoothsm.cameraapp.presentation.ui.screen.Screen
import com.smoothsm.cameraapp.presentation.ui.theme.Surface

@Composable
fun BottomNavigationBar(
    currentDestination: NavDestination?,
    onNavigate: (Any) -> Unit,
) {
    NavigationBar(
        modifier =
            Modifier
                .windowInsetsPadding(WindowInsets.navigationBars),
        containerColor = Surface,
    ) {
        Screen.bottomNavScreen.forEachIndexed { index, bottom ->
            NavigationBarItem(
                selected =
                    currentDestination?.hierarchy?.any {
                        it.hasRoute(bottom.route::class)
                    } ?: false,
                icon = { Icon(bottom.icon, bottom.title) },
                label = { Text(bottom.title) },
                onClick = { onNavigate(bottom.route) },
            )
        }
    }
}
