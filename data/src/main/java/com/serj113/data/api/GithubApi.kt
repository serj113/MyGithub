package com.serj113.data.api

import com.serj113.data.model.UserList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") user: String,
        @Query("page") page: Long,
        @Query("per_page") perPage: Int = 8,
        @Query("order") order: String = "ASC"
    ): Response<UserList>
}