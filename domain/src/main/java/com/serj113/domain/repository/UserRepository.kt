package com.serj113.domain.repository

import com.serj113.domain.base.Entity
import com.serj113.domain.entity.User
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    suspend fun searchUser(keyword: String, page: Long): StateFlow<Entity<List<User>>>
}