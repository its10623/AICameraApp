package com.smoothsm.cameraapp.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.smoothsm.cameraapp.domain.usecase.UserUseCase
import com.smoothsm.cameraapp.presentation.base.BaseViewModel
import com.smoothsm.cameraapp.presentation.contract.SignInContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel
@Inject
constructor(
    private val userUseCase: UserUseCase,
) :
    BaseViewModel<SignInContract.Intent, SignInContract.State, SignInContract.SideEffect>() {
    override fun createInitialState() = SignInContract.State()

    override fun onIntent(intent: SignInContract.Intent) {
        when (intent) {
            is SignInContract.Intent.EmailChanged -> setState {
                copy(email = intent.email)
            }
            is SignInContract.Intent.PasswordChanged -> setState {
                copy(password = intent.password)
            }
            is SignInContract.Intent.TogglePasswordVisible -> setState {
                copy(isPasswordVisible = !isPasswordVisible)
            }
            is SignInContract.Intent.SignIn -> {
                viewModelScope.launch {
                    try {
                        val user = userUseCase.signIn(
                            email = currentState.email,
                            password = currentState.password
                        )
                        setSideEffect { SignInContract.SideEffect.NavigateToMain(user.uid) }
                    } catch (e: Exception) {
                        setSideEffect { SignInContract.SideEffect.ShowError(e.message ?: "로그인 실패") }
                    }
                }
            }
            is SignInContract.Intent.GoogleSignIn -> {
                viewModelScope.launch {
                    try {
                        val user = userUseCase.signInWithGoogle(idToken = intent.idToken)
                        setSideEffect { SignInContract.SideEffect.NavigateToMain(user.uid) }
                    } catch (e: Exception) {
                        setSideEffect { SignInContract.SideEffect.ShowError(e.message ?: "로그인 실패") }
                    }
                }
            }
        }
    }
}
