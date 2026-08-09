package com.smoothsm.cameraapp.presentation.contract

import com.smoothsm.cameraapp.presentation.base.UiIntent
import com.smoothsm.cameraapp.presentation.base.UiSideEffect
import com.smoothsm.cameraapp.presentation.base.UiState

object SignUpContract {
    data class State (
        val nickname: String = "",
        val email: String = "",
        val password: String = "",
        val passwordConfirm: String = "",
        val isPasswordVisible: Boolean = false,
        val isPasswordConfirmVisible: Boolean = false,
        val isLoading: Boolean = false,
        val emailError: String? = null,
        val passwordError: String? = null,
        val passwordConfirmError: String? = null,
        ): UiState

    sealed class Intent : UiIntent {
        data class NicknameChanged(val nickname: String) : Intent()
        data class EmailChanged(val email: String) : Intent()
        data class PasswordChanged(val password: String) : Intent()
        data class PasswordConfirmChanged(val passwordConfirm: String) : Intent()
        data object TogglePasswordVisible : Intent()
        data object TogglePasswordConfirmVisible : Intent()
        data object SignUp : Intent()
    }

    sealed class SideEffect : UiSideEffect {
        data class NavigateToMain(val uid: String) : SideEffect()
        data class ShowError(val message: String) : SideEffect()
    }
}