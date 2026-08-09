package com.smoothsm.cameraapp.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.smoothsm.cameraapp.domain.usecase.UserUseCase
import com.smoothsm.cameraapp.presentation.base.BaseViewModel
import com.smoothsm.cameraapp.presentation.contract.SignUpContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
@Inject
constructor(
    private val userUseCase: UserUseCase,
) : BaseViewModel<SignUpContract.Intent, SignUpContract.State, SignUpContract.SideEffect>() {
    override fun createInitialState() = SignUpContract.State()

    override fun onIntent(intent: SignUpContract.Intent) {
        when (intent) {
            is SignUpContract.Intent.NicknameChanged -> setState {
                copy(nickname = intent.nickname)
            }

            is SignUpContract.Intent.EmailChanged -> {
                val error = if (intent.email.isEmpty()) "이메일을 입력해 주세요" else null
                //TODO Validator 검증 필
                setState { copy(email = intent.email, emailError = error) }
            }

            is SignUpContract.Intent.PasswordChanged -> {
                val error = if (currentState.password.isNotEmpty() &&
                    intent.password != currentState.passwordConfirm
                ) "비밀번호가 일치하지 않습니다" else null
                setState { copy(password = intent.password, passwordConfirmError = error) }
            }

            is SignUpContract.Intent.PasswordConfirmChanged -> {
                val error = if (intent.passwordConfirm.isNotEmpty() &&
                    intent.passwordConfirm != currentState.password
                ) "비밀번호가 일치하지 않습니다" else null
                setState { copy(passwordConfirm = intent.passwordConfirm, passwordConfirmError = error) }
            }

            is SignUpContract.Intent.TogglePasswordVisible -> setState {
                copy(isPasswordVisible = !isPasswordVisible)
            }

            is SignUpContract.Intent.TogglePasswordConfirmVisible -> setState {
                copy(isPasswordConfirmVisible = !isPasswordConfirmVisible)
            }

            is SignUpContract.Intent.SignUp -> {
                viewModelScope.launch {
                    try {
                        val user = userUseCase.signUp(
                            nickname = currentState.nickname,
                            email = currentState.email,
                            password = currentState.password
                        )
                        setSideEffect { SignUpContract.SideEffect.NavigateToMain(user.uid) }
                    } catch (e: Exception) {
                        setSideEffect {
                            SignUpContract.SideEffect.ShowError(
                                e.message ?: "회원가입 실패"
                            )
                        }
                    }
                }
            }

            else -> {}
        }
    }
}
