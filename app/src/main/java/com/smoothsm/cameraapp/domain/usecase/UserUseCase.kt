package com.smoothsm.cameraapp.domain.usecase

import com.smoothsm.cameraapp.domain.model.User

interface UserUseCase {
    suspend fun signUp(email: String, password: String, nickname: String): User
    suspend fun signIn(email: String, password: String): User
    suspend fun signOut()
    suspend fun signInWithGoogle(idToken: String): User
    fun getCurrentUser(): User?
    suspend fun deleteAccount()
}
