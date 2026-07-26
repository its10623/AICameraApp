package com.smoothsm.cameraapp.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.smoothsm.cameraapp.presentation.ui.theme.Primary

@Composable
fun AiMark(
    modifier: Modifier = Modifier,
    markModifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .clip(shape = RoundedCornerShape(6.dp))
                .background(Primary.copy(0.3f))
                .padding(horizontal = 2.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "✦",
            modifier =
                markModifier
                    .padding(bottom = 1.dp, start = 1.dp, end = 1.dp),
            style = MaterialTheme.typography.labelSmall,
            color = Primary,
        )
    }
}
