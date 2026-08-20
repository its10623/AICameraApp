package com.smoothsm.cameraapp.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.smoothsm.cameraapp.domain.model.Receipt
import com.smoothsm.cameraapp.domain.model.chipBg
import com.smoothsm.cameraapp.domain.model.icon
import com.smoothsm.cameraapp.domain.model.iconTint
import com.smoothsm.cameraapp.domain.model.label
import com.smoothsm.cameraapp.presentation.ui.theme.Expense
import com.smoothsm.cameraapp.presentation.ui.theme.ModalBg
import com.smoothsm.cameraapp.presentation.ui.theme.Primary
import com.smoothsm.cameraapp.presentation.ui.theme.Shape
import com.smoothsm.cameraapp.presentation.ui.theme.Surface
import com.smoothsm.cameraapp.presentation.ui.theme.TextSub
import com.smoothsm.cameraapp.presentation.ui.util.formatDayOfMonth
import com.smoothsm.cameraapp.presentation.ui.util.formatReceiptAmount

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptDetailBottomSheet(
    receipt: Receipt,
    onDismiss: () -> Unit,
) {
    val formatCreatedDate = formatDayOfMonth(receipt.createdAt)
    val sheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
        )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        sheetState = sheetState,
        scrimColor = ModalBg,
        dragHandle = null,
        containerColor = Surface,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f),
        ) {
            Row(
                modifier =
                    Modifier
                        .padding(horizontal = 12.dp, vertical = 20.dp)
                        .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(52.dp)
                            .clip(shape = Shape.IconBox)
                            .background(receipt.category.chipBg),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = receipt.category.icon,
                        contentDescription = receipt.category.label,
                        tint = receipt.category.iconTint,
                        modifier =
                            Modifier
                                .size(46.dp),
                    )
                }
                Column(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                ) {
                    Text(
                        text = receipt.storeName ?: "",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier =
                            Modifier
                                .padding(4.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth(),
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .clip(shape = Shape.Chip)
                                    .background(receipt.category.chipBg)
                                    .padding(horizontal = 8.dp, vertical = 2.dp),
                        ) {
                            Text(
                                text = receipt.category.label,
                                style = MaterialTheme.typography.bodySmall,
                                color = receipt.category.iconTint,
                            )
                        }

                        Text(
                            text = formatCreatedDate,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSub,
                            modifier =
                                Modifier
                                    .padding(horizontal = 4.dp, vertical = 2.dp),
                        )
                    }
                }

                IconButton(
                    onClick = onDismiss,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = "닫기",
                        modifier =
                            Modifier
                                .size(35.dp),
                        tint = TextSub,
                    )
                }
            }
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "결제 금액",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSub,
                )

                Text(
                    text = formatReceiptAmount(receipt.totalPrice ?: 0, isIncome = false),
                    style = MaterialTheme.typography.displayLarge,
                    color = Expense,
                )
            }

            ListDivider(
                modifier =
                    Modifier
                        .fillMaxWidth(),
            )
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AiMark()
                Text(
                    text = "AI가 ${receipt.items.size}개 품목을 인식했어요",
                    style = MaterialTheme.typography.bodySmall,
                    color = Primary,
                    modifier =
                        Modifier
                            .padding(start = 2.dp),
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "수량 · 단가",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSub,
                    modifier =
                        Modifier
                            .padding(end = 2.dp),
                )
            }
            ReceiptItemList(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                receipt = receipt,
                5,
            )
            ListDivider(
                modifier =
                    Modifier
                        .fillMaxWidth(),
            )

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SecondaryButton(
                    text = "내용 수정",
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(end = 6.dp),
                )
                PrimaryButton(
                    text = "영수증 보기",
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(start = 6.dp),
                )
            }
        }
    }
}
