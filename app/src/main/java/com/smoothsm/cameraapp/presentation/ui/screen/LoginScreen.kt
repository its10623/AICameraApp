package com.smoothsm.cameraapp.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smoothsm.cameraapp.R
import com.smoothsm.cameraapp.presentation.contract.LoginContract
import com.smoothsm.cameraapp.presentation.ui.component.PrimaryButton
import com.smoothsm.cameraapp.presentation.ui.theme.Border
import com.smoothsm.cameraapp.presentation.ui.theme.Expense
import com.smoothsm.cameraapp.presentation.ui.theme.Primary
import com.smoothsm.cameraapp.presentation.ui.theme.TextSub
import com.smoothsm.cameraapp.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToMain: (String) -> Unit,
    onNavigateToFindPassword: (String) -> Unit,
    onNavigateToSignUp: () -> Unit,
    isPasswordVisible: Boolean = false,
) {
    var email by remember { mutableStateOf("test@test.com") }
    var password by remember { mutableStateOf("test20260615!") }

    var passwordVisible by remember { mutableStateOf(isPasswordVisible) }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.uiSideEffect.collect { effect ->
            when (effect) {
                is LoginContract.SideEffect.NavigateToMain ->
                    onNavigateToMain(effect.userKey)
                is LoginContract.SideEffect.ShowError -> {
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
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "앱 로고",
                modifier =
                    Modifier
                        .size(70.dp),
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "찰칵 가계부",
                style = MaterialTheme.typography.displayMedium,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "영수증 찍으면 AI가 알아서 정리",
                style = MaterialTheme.typography.bodySmall,
                color = TextSub,
            )

            Spacer(modifier = Modifier.height(24.dp))

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
                value = email,
                onValueChange = { email = it },
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
                    text = "비밀번호",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSub,
                )
            }

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                isError = false,
                visualTransformation =
                    if (isPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                trailingIcon = {
                    TextButton(
                        onClick = { passwordVisible = !passwordVisible },
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

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(end = 32.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(
                    onClick = { onNavigateToFindPassword(email) },
                ) {
                    Text(
                        text = "비밀번호를 잊어버리셨나요?",
                        color = TextSub,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }

            PrimaryButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                enabled = email.isNotBlank() && password.isNotBlank(),
                text = "로그인",
                onClick = { onNavigateToMain(email) },
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "아직 회원이 아니신가요?",
                    color = TextSub,
                    style = MaterialTheme.typography.bodySmall,
                )
                TextButton(
                    onClick = { onNavigateToSignUp() },
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Text(
                        text = "회원가입",
                        color = Primary,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}
