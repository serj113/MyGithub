package com.serj113.domain

import com.serj113.domain.interactor.SearchUserUseCase
import com.serj113.domain.repository.UserRepository
import com.serj113.domain.usecase.SearchUserUseCaseImpl
import fr.xgouchet.elmyr.junit4.ForgeRule
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchUseCaseImplTest {
    private val mockUserRepository = mockk<UserRepository>()

    private lateinit var searchUseCase: SearchUserUseCase
    @Rule
    @JvmField
    val forge = ForgeRule()

    @Before
    fun setup() {
        searchUseCase = SearchUserUseCaseImpl(mockUserRepository)
    }

    @Test
    fun `search user should return list of user`() {
        val keyword = forge.aString()
        val page = forge.aLong()
        val args = SearchUserUseCase.Args(keyword, page)

        val users = searchUseCase.invoke(args)


    }
}