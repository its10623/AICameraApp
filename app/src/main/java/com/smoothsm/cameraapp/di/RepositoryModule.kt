package com.smoothsm.cameraapp.di

import com.smoothsm.cameraapp.data.repository.CameraRepositoryImpl
import com.smoothsm.cameraapp.data.repository.ScanRepositoryImpl
import com.smoothsm.cameraapp.data.repository.UserRepositoryImpl
import com.smoothsm.cameraapp.domain.repository.CameraRepository
import com.smoothsm.cameraapp.domain.repository.ScanRepository
import com.smoothsm.cameraapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCameraRepository(impl: CameraRepositoryImpl): CameraRepository

    @Binds
    @Singleton
    abstract fun bindScanRepository(impl: ScanRepositoryImpl): ScanRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}
