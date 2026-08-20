package com.smoothsm.cameraapp.presentation.ui.component

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smoothsm.cameraapp.presentation.ui.theme.Border

@Composable
fun ListDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        thickness = 1.dp,
        color = Border.copy(alpha = 0.3f),
    )
}
