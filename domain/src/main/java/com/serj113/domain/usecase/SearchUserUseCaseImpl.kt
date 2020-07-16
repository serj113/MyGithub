package com.serj113.domain.usecase

import com.serj113.domain.interactor.SearchUserUseCase
import com.serj113.domain.repository.UserRepository
import javax.inject.Inject

class SearchUserUseCaseImpl @Inject constructor(
    private var userRepository: UserRepository
) : SearchUserUseCase() {
    override suspend fun invoke(args: Args) = userRepository.searchUser(args.keyword, args.page)
}