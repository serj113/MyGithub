package com.serj113.presentation.datasource

import androidx.lifecycle.MutableLiveData
import com.serj113.domain.base.NetworkState
import com.serj113.domain.entity.User
import com.serj113.domain.interactor.SearchUserUseCase
import com.serj113.presentation.base.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class PageKeyedGithubUserDataSource constructor(
    private val searchUserUseCase: SearchUserUseCase,
    private val query: String,
    private val pageSize: Int,
    private val errorMessageMutableLiveData: MutableLiveData<String>
) : BaseDataSource<Long, User>() {
    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, User>
    ) {
        if (query.isEmpty()) return
        val mutableListUsers = mutableListOf<User>()
        var isError = false
        postError("")
        GlobalScope.launch(Dispatchers.Default) {
            searchUserUseCase.invoke(SearchUserUseCase.Args(query, page, pageSize))
                .onEach { entity ->
                    entity.data?.let {
                        mutableListUsers.addAll(it)
                    }
                    if (entity.status == NetworkState.ERROR) {
                        isError = true
                        entity.message?.let { message ->
                            postError(message)
                        }
                    }
                }
                .onCompletion {
                    if (!isError) callback.onResult(mutableListUsers, null, page)
                }
                .collect()
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, User>) {
        val mutableListUsers = mutableListOf<User>()
        var isError = false
        postError("")
        page += 1
        GlobalScope.launch(Dispatchers.Default) {
            searchUserUseCase.invoke(SearchUserUseCase.Args(query, page, pageSize))
                .onEach { entity ->
                    entity.data?.let {
                        mutableListUsers.addAll(it)
                    }
                    if (entity.status == NetworkState.ERROR) {
                        isError = true
                        entity.message?.let { message ->
                            postError(message)
                        }
                    }
                }
                .onCompletion {
                    if (!isError) callback.onResult(mutableListUsers, page)
                }
                .collect()
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, User>) = Unit

    fun postError(message: String) {
        GlobalScope.launch(Dispatchers.Main) {
            errorMessageMutableLiveData.value = message
        }
    }
}