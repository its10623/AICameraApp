package com.smoothsm.cameraapp.presentation.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.smoothsm.cameraapp.presentation.ui.theme.Surface

@Composable
fun PrimaryButton(
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    modifier: Modifier,
    text: String,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f)

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier =
            modifier
                .scale(scale),
        interactionSource = interactionSource,
        shape = RoundedCornerShape(16.dp),
        colors =
            ButtonDefaults.buttonColors(
                contentColor = Surface,
            ),
    ) {
        Text(
            text = text,
            modifier =
                Modifier
                    .padding(8.dp),
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}
