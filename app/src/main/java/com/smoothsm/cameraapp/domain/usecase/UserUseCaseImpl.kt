package com.smoothsm.cameraapp.domain.usecase

import com.smoothsm.cameraapp.domain.model.User
import com.smoothsm.cameraapp.domain.repository.UserRepository
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
) : UserUseCase {
    override suspend fun signUp(
        email: String,
        password: String,
        nickname: String
    ): User {
        userRepository.signUp(email, password)
        return userRepository.updateNickname(nickname)
    }

    override suspend fun signIn(email: String, password: String): User =
        userRepository.signIn(email, password)


    override suspend fun signOut() = userRepository.signOut()

    override suspend fun signInWithGoogle(idToken: String): User = userRepository.signInWithGoogle(idToken)
}
