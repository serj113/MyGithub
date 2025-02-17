package com.serj113.data.di

import com.serj113.data.BuildConfig
import com.serj113.data.api.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class ApiModule {
//    @Provides
//    @Singleton
//    internal fun provideOkHttpClient(): OkHttpClient {
//        return OkHttpClient.Builder().build()
//    }

    @Provides
    @Singleton
    internal fun provideRetrofitInterface(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    internal fun provideGithubApi(retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }
}