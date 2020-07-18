package com.serj113.domain.interactor

import com.serj113.domain.base.Entity
import com.serj113.domain.base.FlowUseCase
import com.serj113.domain.entity.User

abstract class SearchUserUseCase : FlowUseCase<SearchUserUseCase.Args, Entity<List<User>>>() {
    data class Args(
        val keyword: String,
        val page: Long,
        val pageSize: Int
    )
}