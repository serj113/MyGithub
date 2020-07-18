package com.serj113.data.repository

import com.serj113.data.api.GithubApi
import com.serj113.data.model.toUserEntities
import com.serj113.domain.base.Entity
import com.serj113.domain.base.NetworkState
import com.serj113.domain.entity.User
import com.serj113.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi
) : UserRepository {
    override suspend fun searchUser(
        keyword: String,
        page: Long
    ): Flow<Entity<List<User>>> = flow {
        emit(Entity<List<User>>(null, null, NetworkState.LOADING))
        val result = githubApi.searchUser(keyword, page)
        if (result.isSuccessful) {
            result.body()?.let {
                emit(Entity(it.items.toUserEntities(), null, NetworkState.SUCCESS))
            }
        } else {
            emit(Entity<List<User>>(null, result.message(), NetworkState.ERROR))
        }
    }.flowOn(Dispatchers.IO)
}