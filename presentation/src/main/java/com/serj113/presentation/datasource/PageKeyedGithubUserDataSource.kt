package com.serj113.presentation.datasource

import com.serj113.domain.entity.User
import com.serj113.domain.interactor.SearchUserUseCase
import com.serj113.presentation.base.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PageKeyedGithubUserDataSource constructor(
    private val searchUserUseCase: SearchUserUseCase,
    private val query: String
) : BaseDataSource<Long, User>() {
    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, User>
    ) {
        val mutableListUsers = mutableListOf<User>()
        GlobalScope.launch(Dispatchers.Main) {
            searchUserUseCase.invoke(SearchUserUseCase.Args(query, page))
                .onEach { entity ->
                    entity.data?.let {
                        mutableListUsers.addAll(it)
                    }
                }
                .onCompletion {
                    callback.onResult(mutableListUsers, null, page)
                }
                .collect()
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, User>) {
        val mutableListUsers = mutableListOf<User>()
        page += 1
        GlobalScope.launch(Dispatchers.Main) {
            searchUserUseCase.invoke(SearchUserUseCase.Args(query, page))
                .onEach { entity ->
                    entity.data?.let {
                        mutableListUsers.addAll(it)
                    }
                }
                .onCompletion {
                    callback.onResult(mutableListUsers, page)
                }
                .collect()
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, User>) {
        TODO("Not yet implemented")
    }

}