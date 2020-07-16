package com.serj113.data.repository

import com.serj113.data.api.GithubApi
import com.serj113.data.model.toUserEntities
import com.serj113.domain.base.Entity
import com.serj113.domain.base.NetworkState
import com.serj113.domain.entity.User
import com.serj113.domain.repository.UserRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi
) : UserRepository {
    override suspend fun searchUser(
        keyword: String,
        page: Long
    ): StateFlow<Entity<List<User>>> {
        val result = MutableStateFlow<Entity<List<User>>>(Entity(null, null, NetworkState.LOADING))
        GlobalScope.launch(Dispatchers.Default) {
            val response = githubApi.searchUser(keyword, page)
            result.value = Entity(
                response.items.toUserEntities(),
                null,
                NetworkState.SUCCESS
            )
        }
        return result
    }
}