package com.smoothsm.cameraapp.domain.validator.user

object PasswordValidator {
    const val MIN_LENGTH = 8
    const val MAX_LENGTH = 16

    fun validate(password: String): String? {
        return when {
            password.length < MIN_LENGTH -> "8자 이상 입력해 주세요"
            password.length > MAX_LENGTH -> "16자 이하로 입력해 주세요"
            password.contains(" ") -> "공백을 포함할 수 없습니다"
            password.none { it.isDigit() } -> "숫자를 포함해 주세요"
            password.none { !it.isLetterOrDigit() } -> "특수문자를 포함해 주세요"
            else -> null
        }
    }
}