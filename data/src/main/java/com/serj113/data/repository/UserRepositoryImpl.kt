package com.serj113.data.repository

import com.serj113.data.api.GithubApi
import com.serj113.data.model.toUserEntities
import com.serj113.domain.base.Entity
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
        page: Long,
        pageSize: Int
    ): Flow<Entity<List<User>>> = flow {
        emit(Entity.loading<List<User>>())
        try {
            val users = githubApi.searchUser(keyword, page, pageSize).items.toUserEntities()
            emit(Entity.success(users))
        } catch (t: Throwable) {
            emit(Entity.error<List<User>>(t.localizedMessage))
        }
    }.flowOn(Dispatchers.Default)
}