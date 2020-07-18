package com.serj113.presentation.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.serj113.domain.entity.User
import com.serj113.domain.interactor.SearchUserUseCase
import com.serj113.presentation.datasource.PageKeyedGithubUserDataSource
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UserSearchViewModel @ViewModelInject constructor(
    private val searchUserUseCase: SearchUserUseCase
) : ViewModel() {
    val queryChannel = ConflatedBroadcastChannel<String>()
    val searchPagedListLiveData = initializeSearchListLiveData()
    private var githubUserDataSource: PageKeyedGithubUserDataSource? = null

    init {
        queryChannel.asFlow()
            .debounce(QUERY_DEBOUNCE)
            .onEach { githubUserDataSource?.invalidate() }
            .launchIn(viewModelScope)
    }

    private fun initializeSearchListLiveData(): LiveData<PagedList<User>> {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .setEnablePlaceholders(false)
            .build()

        val dataSource = object : DataSource.Factory<Long, User>() {
            override fun create(): PageKeyedGithubUserDataSource {
                return PageKeyedGithubUserDataSource(
                    searchUserUseCase,
                    queryChannel.valueOrNull.orEmpty()
                ).also {
                    githubUserDataSource = it
                }
            }
        }

        return LivePagedListBuilder(dataSource, config).build()
    }

    companion object {
        private const val QUERY_DEBOUNCE = 500L
        private const val PAGE_SIZE = 20
        private const val PREFETCH_DISTANCE = 20
    }
}