package com.serj113.domain.interactor

import com.serj113.domain.base.Entity
import com.serj113.domain.base.StateFlowUseCase
import com.serj113.domain.entity.User

abstract class SearchUserUseCase : StateFlowUseCase<SearchUserUseCase.Args, Entity<List<User>>>() {
    data class Args(
        val keyword: String,
        val page: Long
    )
}