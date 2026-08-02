package com.smoothsm.cameraapp.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.smoothsm.cameraapp.presentation.ui.theme.CategoryGrocery
import com.smoothsm.cameraapp.presentation.ui.theme.CategoryGroceryBg
import com.smoothsm.cameraapp.presentation.ui.theme.Shape
import com.smoothsm.cameraapp.presentation.ui.theme.Surface

@Composable
fun CautionCard(text: String) {
    Card(
        modifier =
            Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth(),
        shape = Shape.SummaryCard,
        colors = CardDefaults.cardColors(containerColor = CategoryGroceryBg.copy(alpha = 0.5f)),
        border = BorderStroke(1.dp, CategoryGrocery),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(16.dp)
                        .clip(Shape.Chip)
                        .background(CategoryGrocery),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "!",
                    style = MaterialTheme.typography.bodySmall,
                    color = Surface,
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = CategoryGrocery,
            )
        }
    }
}
