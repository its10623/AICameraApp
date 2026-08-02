package com.smoothsm.cameraapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.smoothsm.cameraapp.domain.model.User
import com.smoothsm.cameraapp.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) : UserRepository {
    override suspend fun signUp(
        email: String,
        password: String,
    ): User {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val firebaseUser = result.user ?: throw Exception("회원가입 실패")
        return User(
            uid = firebaseUser.uid,
            email = firebaseUser.email,
        )
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): User {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        val user = result.user ?: throw Exception("로그인 실패")
        return User(
            uid = user.uid,
            email = user.email,
            nickname = user.displayName
        )
    }

    override suspend fun signOut() {
        auth.signOut()
    }


    override suspend fun updateNickname(nickname: String): User {
        val firebaseUser = auth.currentUser ?: throw Exception("로그인 상태가 아닙니다.")
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(nickname)
            .build()
        firebaseUser.updateProfile(profileUpdates).await()

        return User(
            uid = firebaseUser.uid,
            email = firebaseUser.email,
            nickname = firebaseUser.displayName
        )
    }
}
