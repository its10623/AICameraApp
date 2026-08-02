package com.smoothsm.cameraapp.domain.repository

import com.smoothsm.cameraapp.domain.model.User

interface UserRepository {
    suspend fun signUp(email: String, password: String): User
    suspend fun updateNickname(nickname: String): User
}
