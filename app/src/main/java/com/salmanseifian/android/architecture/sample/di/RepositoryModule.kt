package com.salmanseifian.android.architecture.sample.di

import com.salmanseifian.android.architecture.sample.data.repository.RemoteRepository
import com.salmanseifian.android.architecture.sample.data.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRepository(remoteRepository: RemoteRepository): Repository
}