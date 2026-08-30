package com.smoothsm.cameraapp.domain.usecase

import com.smoothsm.cameraapp.domain.model.User
import com.smoothsm.cameraapp.domain.repository.UserRepository
import com.smoothsm.cameraapp.domain.validator.user.EmailValidator
import com.smoothsm.cameraapp.domain.validator.user.PasswordValidator
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,

) : UserUseCase {
    override suspend fun signUp(
        email: String,
        password: String,
        nickname: String
    ): User {
        EmailValidator.validate(email)?.let { throw IllegalArgumentException(it) }
        PasswordValidator.validate(password)?.let { throw IllegalArgumentException(it) }
        userRepository.signUp(email, password)
        return userRepository.updateNickname(nickname)
    }
    override suspend fun signIn(email: String, password: String): User {
        EmailValidator.validate(email)?.let { throw IllegalArgumentException(it) }
        return userRepository.signIn(email, password)
    }
    override suspend fun signOut() = userRepository.signOut()
    override suspend fun signInWithGoogle(idToken: String): User = userRepository.signInWithGoogle(idToken)
    override fun getCurrentUser(): User? = userRepository.getCurrentUser()
    override suspend fun deleteAccount() = userRepository.deleteAccount()
}
