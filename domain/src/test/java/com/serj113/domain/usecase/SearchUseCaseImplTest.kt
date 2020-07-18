package com.serj113.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.serj113.domain.base.Entity
import com.serj113.domain.entity.User
import com.serj113.domain.interactor.SearchUserUseCase
import com.serj113.domain.repository.UserRepository
import fr.xgouchet.elmyr.junit4.ForgeRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchUseCaseImplTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val forge = ForgeRule()

    private val mockUserRepository = mockk<UserRepository>()

    private lateinit var searchUseCase: SearchUserUseCase

    @Before
    fun setup() {
        searchUseCase = SearchUserUseCaseImpl(mockUserRepository)
    }

    @Test
    fun `search user should return list of user`() = runBlockingTest {
        val query = forge.aString(5)
        val page = forge.aPositiveLong()
        val pageSize = forge.aPositiveInt()
        val args = SearchUserUseCase.Args(query, page, pageSize)
        val users = listOf(
            User(1, "", "", ""),
            User(1, "", "", ""),
            User(1, "", "", ""),
            User(1, "", "", ""),
            User(1, "", "", "")
        )
        val resultEntity: Entity<List<User>> = Entity.success(users)

        coEvery {
            mockUserRepository.searchUser(query, page, pageSize)
        } returns flowOf(resultEntity)

        val flow = searchUseCase.invoke(args)
        val mutableUsers = mutableListOf<User>()

        flow.onEach { entity ->
            entity.data?.let {
                mutableUsers.addAll(it)
            }
        }.collect()
        assertThat(mutableUsers.count(), equalTo(resultEntity.data?.count()))
    }
}