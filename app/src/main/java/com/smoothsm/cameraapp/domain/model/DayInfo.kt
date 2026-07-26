package com.smoothsm.cameraapp.domain.model

import java.time.LocalDate

data class DayInfo(
    val date: LocalDate,
    val totalIncome: Int = 0,
    val totalExpense: Int = 0,
    val isSelected: Boolean = false,
)
