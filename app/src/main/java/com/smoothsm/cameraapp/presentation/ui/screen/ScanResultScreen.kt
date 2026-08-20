package com.smoothsm.cameraapp.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.smoothsm.cameraapp.presentation.ui.component.AiMark
import com.smoothsm.cameraapp.presentation.ui.component.BackButton
import com.smoothsm.cameraapp.presentation.ui.component.CautionCard
import com.smoothsm.cameraapp.presentation.ui.component.CheckPrimaryIcon
import com.smoothsm.cameraapp.presentation.ui.component.ListDivider
import com.smoothsm.cameraapp.presentation.ui.component.PrimaryButton
import com.smoothsm.cameraapp.presentation.ui.component.ReceiptItemList
import com.smoothsm.cameraapp.presentation.ui.component.SecondaryButton
import com.smoothsm.cameraapp.presentation.ui.theme.BgApp
import com.smoothsm.cameraapp.presentation.ui.theme.Expense
import com.smoothsm.cameraapp.presentation.ui.theme.Primary
import com.smoothsm.cameraapp.presentation.ui.theme.Shape
import com.smoothsm.cameraapp.presentation.ui.theme.Surface
import com.smoothsm.cameraapp.presentation.ui.theme.TextSub
import com.smoothsm.cameraapp.presentation.ui.util.formatPurchaseAt
import com.smoothsm.cameraapp.presentation.ui.util.formatReceiptAmount

@Composable
fun ScanResultScreen(
    // viewModel: ScanViewModel = hiltViewModel(),
    receipt: Receipt,
) {
    // val scanResult by viewModel.uiState.collectAsStateWithLifecycle()
    val formatCreatedDate = formatPurchaseAt(receipt.createdAt)

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize(),
        topBar = {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .statusBarsPadding(),
            ) {
                BackButton(
                    modifier =
                        Modifier
                            .align(Alignment.CenterStart)
                            .size(36.dp),
                    onClick = { },
                    // TODO: 뒤로가기
                    imageVector = Icons.Rounded.ChevronLeft,
                    tint = TextSub,
                )

                Text(
                    text = "스캔 결과",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        },
        bottomBar = {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(BgApp)
                        .padding(12.dp)
                        .navigationBarsPadding(),
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
                    text = "가계부에 저장",
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(start = 6.dp),
                )
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding()),
            contentPadding = PaddingValues(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    CheckPrimaryIcon(
                        modifier = Modifier.size(60.dp),
                        iconModifier = Modifier.size(40.dp),
                        status = true,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "분석이 완료됐어요!",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AiMark()
                        Text(
                            text = "AI가 영수증을 자동으로 정리했어요",
                            style = MaterialTheme.typography.bodySmall,
                            color = Primary,
                            modifier = Modifier.padding(start = 2.dp),
                        )
                    }
                }
            }
            item {
                Card(
                    modifier =
                        Modifier
                            .padding(horizontal = 12.dp)
                            .fillMaxWidth(),
                    shape = Shape.SummaryCard,
                    colors = CardDefaults.cardColors(containerColor = Surface),
                ) {
                    Column(
                        modifier =
                        Modifier,
                    ) {
                        Row(
                            modifier =
                                Modifier
                                    .padding(horizontal = 12.dp, vertical = 12.dp)
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
                                        .padding(12.dp),
                            ) {
                                Text(
                                    text = receipt.storeName ?: "",
                                    style = MaterialTheme.typography.headlineMedium,
                                    modifier =
                                        Modifier
                                            .padding(bottom = 4.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )

                                Row(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
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
                                }
                            }
                        }
                        ListDivider(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp),
                        )
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "날짜",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSub,
                            )

                            Text(
                                text = formatCreatedDate,
                                style = MaterialTheme.typography.bodySmall,
                                modifier =
                                    Modifier
                                        .padding(horizontal = 4.dp, vertical = 2.dp),
                            )
                        }
                        ListDivider(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp),
                        )
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "구분",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSub,
                            )
                            Box(
                                modifier =
                                    Modifier
                                        .clip(shape = Shape.Chip)
                                        .background(Expense.copy(alpha = 0.15f))
                                        .padding(horizontal = 8.dp, vertical = 2.dp),
                            ) {
                                Text(
                                    text = "지출",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Expense,
                                )
                            }
                        }
                    }
                }
            } // item

            item {
                Card(
                    modifier =
                        Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                    shape = Shape.SummaryCard,
                    colors = CardDefaults.cardColors(containerColor = Surface),
                ) {
                    Column {
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "인식된 품목",
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${receipt.items.size}개",
                                style = MaterialTheme.typography.titleMedium,
                                color = Primary,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "금액",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSub,
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        }
                        ListDivider(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp),
                        )
                        ReceiptItemList(modifier = Modifier.fillMaxWidth(), receipt = receipt, 3)

                        ListDivider(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp),
                        )
                    }
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "합계",
                            style = MaterialTheme.typography.headlineMedium,
                        )

                        Text(
                            text =
                                formatReceiptAmount(
                                    amount = receipt.totalPrice ?: 0,
                                    isIncome = false,
                                ),
                            style = MaterialTheme.typography.headlineMedium,
                            color = Expense,
                        )
                    }
                }
            }
            item {
            }
        }
    }
    CautionCard("금액이나 품목이 다르면 '내용 수정'에서 바로 수정할 수 있어요")
}
