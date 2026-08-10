package com.smoothsm.cameraapp.presentation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smoothsm.cameraapp.presentation.contract.SignUpContract
import com.smoothsm.cameraapp.presentation.ui.component.BackButton
import com.smoothsm.cameraapp.presentation.ui.component.PrimaryButton
import com.smoothsm.cameraapp.presentation.ui.theme.Border
import com.smoothsm.cameraapp.presentation.ui.theme.Expense
import com.smoothsm.cameraapp.presentation.ui.theme.Primary
import com.smoothsm.cameraapp.presentation.ui.theme.TextSub
import com.smoothsm.cameraapp.presentation.viewmodel.SignUpViewModel

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onNavigateToMain: (String) -> Unit,
    onNavigateBack: () -> Unit = {},
) {
    val state by viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    var isFocused by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.uiSideEffect.collect { effect ->
            when (effect) {
                is SignUpContract.SideEffect.NavigateToMain ->
                    onNavigateToMain(effect.uid)
                is SignUpContract.SideEffect.ShowError -> {
                    snackBarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ) {
                    focusManager.clearFocus()
                },
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
                    onClick = onNavigateBack,
                    imageVector = Icons.Rounded.ChevronLeft,
                    tint = TextSub,
                )

                Text(
                    text = "회원가입",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(0.7f))
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = "닉네임",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSub,
                )
            }

            OutlinedTextField(
                value = state.nickname,
                onValueChange = { viewModel.handleIntent(SignUpContract.Intent.NicknameChanged(it)) },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                isError = false,
                shape = RoundedCornerShape(12.dp),
                colors =
                    TextFieldDefaults.colors(
                        focusedIndicatorColor = Primary,
                        unfocusedIndicatorColor = Border,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        errorCursorColor = Expense,
                    ),
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = "이메일",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSub,
                )
            }

            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.handleIntent(SignUpContract.Intent.EmailChanged(it)) },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                isError = state.emailError != null,
                supportingText = {
                    state.emailError?.let { message ->
                        Text(text = message, color = Expense)
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors =
                    TextFieldDefaults.colors(
                        focusedIndicatorColor = Primary,
                        unfocusedIndicatorColor = Border,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        errorCursorColor = Expense,
                    ),
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = "비밀번호",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSub,
                )
            }

            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.handleIntent(SignUpContract.Intent.PasswordChanged(it)) },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .onFocusChanged { state ->
                            isFocused = state.isFocused
                        },
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                isError = state.passwordError != null,
                supportingText = {
                    Text(
                        text = state.passwordError ?: "비밀번호는 8~16자로 영문,숫자,특수문자를 포함해주세요",
                        color = if (state.passwordError != null) Expense else TextSub
                    )
                },
                visualTransformation =
                    if (state.isPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                trailingIcon = {
                    TextButton(
                        onClick = { viewModel.handleIntent(SignUpContract.Intent.TogglePasswordVisible) },
                    ) {
                        Text(
                            text = "보기",
                            color = TextSub,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors =
                    TextFieldDefaults.colors(
                        focusedIndicatorColor = Primary,
                        unfocusedIndicatorColor = Border,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        errorCursorColor = Expense,
                    ),
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = "비밀번호 확인",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSub,
                )
            }

            OutlinedTextField(
                value = state.passwordConfirm,
                onValueChange = { viewModel.handleIntent(SignUpContract.Intent.PasswordConfirmChanged(it)) },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                isError = state.passwordConfirmError != null,
                supportingText = {
                    state.passwordConfirmError?.let { message ->
                        Text(text = message, color = Expense)
                    }
                },
                visualTransformation =
                    if (state.isPasswordConfirmVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                trailingIcon = {
                    TextButton(
                        onClick = { viewModel.handleIntent(SignUpContract.Intent.TogglePasswordConfirmVisible) },
                    ) {
                        Text(
                            text = "보기",
                            color = TextSub,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors =
                    TextFieldDefaults.colors(
                        focusedIndicatorColor = Primary,
                        unfocusedIndicatorColor = Border,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        errorCursorColor = Expense,
                    ),
            )

            Spacer(modifier = Modifier.height(12.dp))

            PrimaryButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                enabled =
                    state.nickname.isNotBlank() &&
                            state.email.isNotBlank() &&
                            state.password.isNotBlank() &&
                            state.passwordConfirm.isNotBlank() &&
                            state.passwordConfirmError == null,
                text = "회원가입",
                onClick = { viewModel.handleIntent(SignUpContract.Intent.SignUp) },
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
