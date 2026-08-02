package com.smoothsm.cameraapp.presentation.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.smoothsm.cameraapp.presentation.ui.theme.Primary
import com.smoothsm.cameraapp.presentation.ui.theme.Surface
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun CameraFab(
    onCamera: () -> Unit,
    modifier: Modifier,
) {
    val scope = rememberCoroutineScope()
    var pressed by remember { mutableStateOf(false) }
    var fabVisible by remember { mutableStateOf(true) }

    val scale by animateFloatAsState(
        targetValue = if (pressed) 1.15f else 1f,
        animationSpec =
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow,
            ),
        label = "",
    )

    AnimatedVisibility(
        visible = fabVisible,
        enter = scaleIn() + fadeIn(),
        exit = scaleOut() + fadeOut(),
    ) {
        FloatingActionButton(
            onClick = {
                pressed = true
                onCamera()

                scope.launch {
                    delay(150.milliseconds)
                    pressed = false
                }
            },
            shape = RoundedCornerShape(40.dp),
            containerColor = Primary,
            modifier =
                modifier
                    .scale(scale)
                    .size(64.dp)
                    .shadow(elevation = 8.dp, shape = CircleShape)
                    .border(
                        width = 2.dp,
                        color = Surface,
                        shape = CircleShape,
                    ),
        ) {
            Icon(
                imageVector = Icons.Rounded.CameraAlt,
                contentDescription = "카메라",
                tint = Surface,
                modifier =
                    Modifier
                        .size(35.dp),
            )
        }
    }
}
