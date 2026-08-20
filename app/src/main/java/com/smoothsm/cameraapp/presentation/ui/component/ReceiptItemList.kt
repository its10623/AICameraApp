package com.smoothsm.cameraapp.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smoothsm.cameraapp.domain.model.Receipt
import com.smoothsm.cameraapp.presentation.ui.theme.TextSub
import com.smoothsm.cameraapp.presentation.ui.util.formatItemAmount

@Composable
fun ReceiptItemList(
    modifier: Modifier,
    receipt: Receipt,
    take: Int,
) {
    var expanded by remember { mutableStateOf(false) }
    val visibleItems = if (expanded) receipt.items else receipt.items.take(take)

    Column(modifier = modifier) {
        visibleItems.forEach { item ->
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = item.name ?: "인식 오류",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = "${item.quantity}개 · ${formatItemAmount(item.price ?: 0)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSub,
                    )
                }
                Text(
                    text = formatItemAmount(item.price ?: 0),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            ListDivider(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
            )
        }
        if (receipt.items.size > take) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded }
                        .padding(12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = if (expanded) "접기" else "${receipt.items.size - take}개 항목 더보기",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSub,
                )
                Icon(
                    imageVector = if (expanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "",
                    tint = TextSub,
                )
            }
        }
    }
}
