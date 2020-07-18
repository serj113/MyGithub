package com.serj113.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.serj113.data.api.GithubApi
import com.serj113.data.model.Item
import com.serj113.data.model.UserList
import com.serj113.domain.entity.User
import com.serj113.domain.repository.UserRepository
import fr.xgouchet.elmyr.junit4.ForgeRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

class UserRepositoryImplTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    @JvmField
    val forge = ForgeRule()

    val mockGithubApi = mockk<GithubApi>()
    val userRepository: UserRepository = UserRepositoryImpl(mockGithubApi)

    @InternalCoroutinesApi
    @Test
    fun `searchUser should return correct entity when success`() = runBlockingTest {
        val query = forge.aString(5)
        val page = forge.aPositiveLong(true)
        val pageSize = forge.aPositiveInt(true)
        val items = listOf(
            Item("", 1, "", ""),
            Item("", 1, "", ""),
            Item("", 1, "", ""),
            Item("", 1, "", ""),
            Item("", 1, "", "")
        )
        val result = UserList(false, items, 5)
        coEvery { mockGithubApi.searchUser(query, page, pageSize) } returns result

        val flow = userRepository.searchUser(query, page, pageSize)
        val mutableUsers = mutableListOf<User>()

        flow.onEach { entity ->
            entity.data?.let {
                mutableUsers.addAll(it)
            }
        }.collect()
        assertThat(mutableUsers.count(), equalTo(result.items.count()))
    }
}