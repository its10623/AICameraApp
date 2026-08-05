package com.smoothsm.cameraapp.domain.repository

import com.smoothsm.cameraapp.domain.model.User

interface UserRepository {
    suspend fun signIn(email: String, password: String): User
    suspend fun signOut()
    suspend fun signUp(email: String, password: String): User
    suspend fun updateNickname(nickname: String): User
    suspend fun signInWithGoogle(idToken: String): User
}
