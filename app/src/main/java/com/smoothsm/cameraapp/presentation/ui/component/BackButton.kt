package com.smoothsm.cameraapp.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier,
    imageVector: ImageVector,
    tint: Color,
) {
    Box(
        modifier = modifier,
    ) {
        IconButton(
            onClick = { onClick() },
        ) {
            Icon(
                imageVector = imageVector,
                modifier =
                    Modifier
                        .size(35.dp),
                contentDescription = "뒤로가기",
                tint = tint,
            )
        }
    }
}
