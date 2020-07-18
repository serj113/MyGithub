package com.serj113.presentation.factory

import androidx.paging.DataSource
import com.serj113.domain.entity.User
import com.serj113.domain.interactor.SearchUserUseCase
import com.serj113.presentation.base.BaseDataSourceFactory
import com.serj113.presentation.datasource.PageKeyedGithubUserDataSource

//class GithubUserFactory(
//    private val searchUseCase: SearchUserUseCase
//) : BaseDataSourceFactory<Long, User>() {
//    override fun create(): DataSource<Long, User> {
//        return PageKeyedGithubUserDataSource
//    }
//}