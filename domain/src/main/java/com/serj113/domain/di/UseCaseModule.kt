package com.serj113.domain.di

import com.serj113.domain.interactor.SearchUserUseCase
import com.serj113.domain.usecase.SearchUserUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class UseCaseModule {
    @Binds
    @ActivityScoped
    abstract fun provideSearchUserUseCase(
        searchUserUseCaseImpl: SearchUserUseCaseImpl
    ): SearchUserUseCase
}