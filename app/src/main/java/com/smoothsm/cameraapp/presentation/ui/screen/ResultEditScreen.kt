package com.smoothsm.cameraapp.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.smoothsm.cameraapp.domain.model.Category
import com.smoothsm.cameraapp.domain.model.Receipt
import com.smoothsm.cameraapp.domain.model.ReceiptItem
import com.smoothsm.cameraapp.domain.model.iconTint
import com.smoothsm.cameraapp.domain.model.label
import com.smoothsm.cameraapp.presentation.ui.component.BackButton
import com.smoothsm.cameraapp.presentation.ui.component.CategoryChip
import com.smoothsm.cameraapp.presentation.ui.component.CautionCard
import com.smoothsm.cameraapp.presentation.ui.component.DivisionChip
import com.smoothsm.cameraapp.presentation.ui.component.EditTextField
import com.smoothsm.cameraapp.presentation.ui.component.ListDivider
import com.smoothsm.cameraapp.presentation.ui.component.PrimaryButton
import com.smoothsm.cameraapp.presentation.ui.component.SecondaryButton
import com.smoothsm.cameraapp.presentation.ui.theme.BgApp
import com.smoothsm.cameraapp.presentation.ui.theme.Expense
import com.smoothsm.cameraapp.presentation.ui.theme.Primary
import com.smoothsm.cameraapp.presentation.ui.theme.Shape
import com.smoothsm.cameraapp.presentation.ui.theme.Surface
import com.smoothsm.cameraapp.presentation.ui.theme.TextSub
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ResultEditScreen(receipt: Receipt) {
    val dateTime =
        receipt.purchaseAt?.let {
            LocalDateTime.parse(it, DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))
        }
    var storeName by remember { mutableStateOf(receipt.storeName ?: "") }
    var date by remember {
        mutableStateOf(
            dateTime?.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) ?: "",
        )
    }
    var time by remember {
        mutableStateOf(
            dateTime?.format(
                DateTimeFormatter.ofPattern("a h:mm", Locale.KOREAN),
            ) ?: "",
        )
    }
    var category by remember { mutableStateOf(receipt.category) }
    val items = remember { mutableStateListOf(*receipt.items.toTypedArray()) }
    var isDivision by remember { mutableStateOf(true) }
    val divisions = listOf("지출" to true, "수입" to false)

    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0.dp),
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
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        focusManager.clearFocus()
                    },
        ) {
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding()),
                contentPadding = PaddingValues(bottom = paddingValues.calculateBottomPadding()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    CautionCard(text = "AI 인식 결과가 정확하지 않을 수 있어요. 잘못된 부분을 직접 고쳐 주세요.")
                }

                item {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 2.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "기본 정보",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }

                item {
                    Card(
                        modifier =
                            Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                        shape = Shape.SummaryCard,
                        colors = CardDefaults.cardColors(containerColor = Surface),
                    ) {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Text(
                                text = "상호명",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSub,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            EditTextField(
                                label = storeName,
                                modifier = Modifier.fillMaxWidth(),
                                paddingValues = PaddingValues(horizontal = 12.dp, vertical = 16.dp),
                            )
                            Row(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "날짜",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextSub,
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    EditTextField(
                                        label = date,
                                        modifier = Modifier.fillMaxWidth(),
                                        paddingValues =
                                            PaddingValues(
                                                horizontal = 12.dp,
                                                vertical = 16.dp,
                                            ),
                                    )
                                }
                                Spacer(modifier = Modifier.width(24.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "시간",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextSub,
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    EditTextField(
                                        label = time,
                                        modifier = Modifier.fillMaxWidth(),
                                        paddingValues =
                                            PaddingValues(
                                                horizontal = 12.dp,
                                                vertical = 16.dp,
                                            ),
                                    )
                                }
                            }
                            Column(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(),
                            ) {
                                Text(
                                    text = "구분",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSub,
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    divisions.forEach { (division, type) ->
                                        DivisionChip(
                                            isSelected = isDivision == type,
                                            onClick = { isDivision = type },
                                            text = division,
                                            modifier = Modifier.weight(1f),
                                            color = if (type) Expense else Primary,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "카테고리",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    FlowRow(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Category.entries.forEach { cat ->
                            CategoryChip(
                                isSelected = category == cat,
                                onClick = { category = cat },
                                text = cat.label,
                                color = cat.iconTint,
                            )
                        }
                        // TODO 추후 카테고리 직접추가
                    }
                }

                item {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 2.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text =
                                buildAnnotatedString {
                                    append("품목 ")
                                    withStyle(
                                        style =
                                            SpanStyle(
                                                color = Primary,
                                            ),
                                    ) {
                                        append("${items.size}")
                                    }

                                    append("개")
                                },
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }

                item {
                    Card(
                        modifier =
                            Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                        shape = Shape.SummaryCard,
                        colors = CardDefaults.cardColors(containerColor = Surface),
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            items.forEachIndexed { index, item ->
                                key(item.id) {
                                    Row(
                                        modifier =
                                            Modifier
                                                .fillMaxWidth()
                                                .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Box(
                                            modifier =
                                                Modifier
                                                    .clip(Shape.Chip)
                                                    .background(Expense.copy(alpha = 0.15f))
                                                    .clickable(
                                                        interactionSource = remember { MutableInteractionSource() },
                                                        indication = null,
                                                    ) {
                                                        items.removeAt(index)
                                                    }
                                                    .size(20.dp)
                                                    .align(Alignment.CenterVertically),
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.Remove,
                                                contentDescription = "품목 삭제",
                                                tint = Expense.copy(alpha = 0.8f),
                                                modifier =
                                                    Modifier
                                                        .size(20.dp),
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(6.dp))
                                        EditTextField(
                                            label = item.name ?: "",
                                            modifier = Modifier.width(160.dp),
                                            paddingValues =
                                                PaddingValues(
                                                    horizontal = 12.dp,
                                                    vertical = 8.dp,
                                                ),
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        EditTextField(
                                            label = item.quantity.toString(),
                                            modifier =
                                                Modifier
                                                    .width(50.dp),
                                            paddingValues =
                                                PaddingValues(
                                                    horizontal = 12.dp,
                                                    vertical = 8.dp,
                                                ),
                                            textAlign = TextAlign.Center,
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        EditTextField(
                                            label = item.price?.toString() ?: "0",
                                            modifier = Modifier.width(120.dp),
                                            paddingValues =
                                                PaddingValues(
                                                    horizontal = 12.dp,
                                                    vertical = 8.dp,
                                                ),
                                            textAlign = TextAlign.End,
                                        )
                                    }
                                }

                                if (index < items.lastIndex) {
                                    ListDivider(
                                        modifier =
                                            Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 12.dp),
                                    )
                                }
                            }
                        }
                        ListDivider(modifier = Modifier.fillMaxWidth())
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                    ) {
                                        items.add(
                                            ReceiptItem(
                                                id = System.currentTimeMillis(),
                                                name = "",
                                                price = 0,
                                                quantity = 1,
                                            ),
                                        )
                                    }
                                    .padding(12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = "품목 추가",
                                tint = Primary,
                                modifier = Modifier.size(18.dp),
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "품목 추가",
                                style = MaterialTheme.typography.bodySmall,
                                color = Primary,
                            )
                        }
                    }
                }
            }
        }
    }
}
