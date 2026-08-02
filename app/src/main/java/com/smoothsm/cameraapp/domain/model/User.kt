package com.smoothsm.cameraapp.domain.model

data class User(
    val uid: String,
    val email: String?,
    val nickname: String? = null,
)
