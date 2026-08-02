package com.smoothsm.cameraapp.presentation.ui.util

import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.floor

fun formatCalendarAmount(
    amount: Int,
    isIncome: Boolean,
): String {
    val sign = if (isIncome) "+" else "-"
    return if (amount >= 10000) {
        val man = amount / 10000.0
        val formatted =
            if (man == floor(man)) {
                man.toInt().toString()
            } else {
                String.format("%.1f", man)
            }
        "${sign}${formatted}만"
    } else {
        "${sign}$amount"
    }
}

fun formatReceiptAmount(
    amount: Int,
    isIncome: Boolean,
): String {
    val sign = if (isIncome) "+" else "-"
    val formatter = DecimalFormat("#,###")
    return "${sign}${formatter.format(amount)}원"
}

fun formatItemAmount(amount: Int): String {
    val formatter = DecimalFormat("#,###")
    return "${formatter.format(amount)}원"
}

fun formatReceiptPurchaseAt(time: String?): String = time ?: "-"

fun formatTodayInfo(today: LocalDate): String {
    val todayFormatter = DateTimeFormatter.ofPattern("M월 d일 E요일", Locale.KOREAN)
    return today.format(todayFormatter)
}

fun formatDayOfMonth(today: LocalDateTime): String {
    val todayFormatter = DateTimeFormatter.ofPattern("M월 d일 · a h:mm", Locale.KOREAN)
    return today.format(todayFormatter)
}

fun formatDate(date: LocalDateTime): String {
    val todayFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 (E)", Locale.KOREAN)
    return date.format(todayFormatter)
}

fun formatTime(time: LocalDateTime): String {
    val todayFormatter = DateTimeFormatter.ofPattern("a h:mm", Locale.KOREAN)
    return time.format(todayFormatter)
}

fun formatPurchaseAt(today: LocalDateTime): String {
    val todayFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 (E) a h:mm", Locale.KOREAN)
    return today.format(todayFormatter)
}
