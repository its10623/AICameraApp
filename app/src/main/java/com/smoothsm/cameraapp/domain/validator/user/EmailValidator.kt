package com.smoothsm.cameraapp.domain.validator.user

object EmailValidator {
    const val MAX_LENGTH = 100
    fun validate(email: String): String? {
        return when {
            email.length > MAX_LENGTH -> "이메일이 너무 깁니다"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "이메일 형식이 올바르지 않습니다"
            else -> null
        }
    }
}