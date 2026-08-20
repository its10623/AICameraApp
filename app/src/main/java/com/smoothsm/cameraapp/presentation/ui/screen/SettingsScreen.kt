package com.smoothsm.cameraapp.presentation.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smoothsm.cameraapp.presentation.ui.component.Dialog
import com.smoothsm.cameraapp.presentation.ui.component.ListDivider
import com.smoothsm.cameraapp.presentation.ui.theme.BgApp
import com.smoothsm.cameraapp.presentation.ui.theme.Border
import com.smoothsm.cameraapp.presentation.ui.theme.Disabled
import com.smoothsm.cameraapp.presentation.ui.theme.Expense
import com.smoothsm.cameraapp.presentation.ui.theme.Primary
import com.smoothsm.cameraapp.presentation.ui.theme.PrimarySoft
import com.smoothsm.cameraapp.presentation.ui.theme.Shape
import com.smoothsm.cameraapp.presentation.ui.theme.Surface
import com.smoothsm.cameraapp.presentation.ui.theme.TextSub

@Composable
fun SettingsScreen(
    userName: String = "",
    userEmail: String = "",
    onLogout: () -> Unit = {},
    onDeleteAccount: () -> Unit = {},
) {
    var notificationEnabled by remember { mutableStateOf(true) }
    var receiptBackupEnabled by remember { mutableStateOf(true) }

    var showLogoutDialog by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        Dialog(
            title = "로그아웃 하시겠습니까?",
            isTextField = false,
            onDismiss = { showLogoutDialog = false },
            onConfirm = {
                showLogoutDialog = false
                onLogout()
            },
        )
    }

    if (showDeleteAccountDialog) {
        Dialog(
            title = "정말 탈퇴하시겠습니까?",
            label = "탈퇴 시 모든 데이터가 삭제되며 복구할 수 없습니다.",
            confirmText = "탈퇴",
            isTextField = false,
            onDismiss = { showDeleteAccountDialog = false },
            onConfirm = {
                showDeleteAccountDialog = false
                onDeleteAccount()
            },
        )
    }

    Scaffold(
        containerColor = BgApp,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(Surface),
            ) {
                Text(
                    text = "설정",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
                )
                HorizontalDivider(color = Border, thickness = 1.dp)
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier =
                Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Surface),
                    shape = Shape.Card,
                    border = BorderStroke(1.dp, Border),
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(PrimarySoft),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.AccountCircle,
                                contentDescription = "프로필",
                                tint = Primary,
                                modifier = Modifier.size(32.dp),
                            )
                        }

                        Column(
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .padding(start = 13.dp),
                        ) {
                            Text(
                                text = userName.ifBlank { "사용자" },
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = userEmail,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSub,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }

                        Icon(
                            imageVector = Icons.Rounded.ChevronRight,
                            contentDescription = null,
                            tint = TextSub,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
            }

            item {
                SettingsSectionTitle("앱 설정")
                SettingsCard {
                    SettingsToggleRow(
                        label = "알림",
                        checked = notificationEnabled,
                        onCheckedChange = { notificationEnabled = it },
                    )
                    ListDivider()
                    SettingsNavRow(label = "카테고리 관리")
                    ListDivider()
                    SettingsNavRow(label = "예산 알림 설정")
                }
                Spacer(modifier = Modifier.height(18.dp))
            }

            item {
                SettingsSectionTitle("데이터")
                SettingsCard {
                    SettingsToggleRow(
                        label = "영수증 원본 자동 백업",
                        checked = receiptBackupEnabled,
                        onCheckedChange = { receiptBackupEnabled = it },
                    )
                    ListDivider()
                    SettingsNavRow(label = "데이터 내보내기 (CSV)")
                }
                Spacer(modifier = Modifier.height(18.dp))
            }

            item {
                SettingsSectionTitle("정보")
                SettingsCard {
                    SettingsNavRow(label = "공지사항")
                    ListDivider()
                    SettingsNavRow(label = "이용약관 및 개인정보처리방침")
                    ListDivider()
                    SettingsNavRow(
                        label = "버전 정보",
                        value = "1.0.0",
                        showChevron = false,
                    )
                }
                Spacer(modifier = Modifier.height(18.dp))
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "로그아웃",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Expense,
                        modifier =
                            Modifier
                                .clickable { showLogoutDialog = true }
                                .padding(vertical = 6.dp),
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "회원탈퇴",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSub,
                        modifier =
                            Modifier
                                .clickable { showDeleteAccountDialog = true }
                                .padding(vertical = 6.dp),
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun SettingsSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.SemiBold,
        color = TextSub,
        modifier = Modifier.padding(start = 2.dp, bottom = 9.dp),
    )
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = Shape.Card,
        border = BorderStroke(1.dp, Border),
    ) {
        Column(content = content)
    }
}

@Composable
private fun SettingsToggleRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
        )
        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier
                    .size(26.dp)
                    .padding(end = 30.dp),
                colors =
                    SwitchDefaults.colors(
                        checkedThumbColor = Surface,
                        checkedTrackColor = Primary,
                        uncheckedThumbColor = Surface,
                        uncheckedTrackColor = Disabled,
                        uncheckedBorderColor = Disabled,
                    ),
            )
        }
    }
}

@Composable
private fun SettingsNavRow(
    label: String,
    value: String? = null,
    labelColor: Color = Color.Unspecified,
    showChevron: Boolean = true,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .then(
                    if (onClick != null) {
                        Modifier.clickable { onClick() }
                    } else {
                        Modifier
                    },
                )
                .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = labelColor,
            modifier = Modifier.weight(1f),
        )
        if (value != null) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = TextSub,
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        if (showChevron) {
            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = null,
                tint = TextSub,
            )
        }
    }
}
