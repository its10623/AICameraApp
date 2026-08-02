package com.smoothsm.cameraapp.data.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import java.util.UUID
import javax.inject.Inject

class GoogleAuthClient @Inject constructor(
    private val credentialManager: CredentialManager,
) {
    suspend fun signIn(activityContext: Context, serverClientId: String): String {
        val option = GetSignInWithGoogleOption.Builder(serverClientId)
            .setNonce(UUID.randomUUID().toString())
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(option)
            .build()

        val result = credentialManager.getCredential(
            context = activityContext,
            request = request,
        )

        val credential = result.credential
        if (credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            return googleIdTokenCredential.idToken
        }

        throw IllegalStateException("구글 로그인 자격 증명 형식이 올바르지 않습니다.")
    }
}