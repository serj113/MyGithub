package com.serj113.data.di

import com.serj113.data.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class UrlModule {

    @Provides
    @Singleton
    internal fun provideUrl(): String{
        return BuildConfig.BASE_URL
    }
}