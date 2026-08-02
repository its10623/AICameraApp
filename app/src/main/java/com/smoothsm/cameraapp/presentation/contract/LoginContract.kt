package com.smoothsm.cameraapp.presentation.contract

import com.smoothsm.cameraapp.presentation.base.UiIntent
import com.smoothsm.cameraapp.presentation.base.UiSideEffect
import com.smoothsm.cameraapp.presentation.base.UiState

object LoginContract {
    data class State(
        val email: String = "",
        val password: String = "",
        val isPasswordVisible: Boolean = false,
        val isLoading: Boolean = false,
        val emailError: String? = null,
        val passwordError: String? = null,
    ) : UiState

    sealed class Intent : UiIntent {
        data class EmailChanged(val email: String) : Intent()

        data class PasswordChanged(val password: String) : Intent()

        data object TogglePasswordVisible : Intent()

        data object Login : Intent()
        // data class SocialLogin() : Intent()
    }

    sealed class SideEffect : UiSideEffect {
        data class NavigateToMain(val userKey: String) : SideEffect()

        data class ShowError(val message: String) : SideEffect()
    }
}
