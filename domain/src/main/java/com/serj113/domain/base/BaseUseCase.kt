package com.serj113.domain.base

interface BaseUseCase<A, out T: Any> {
    suspend fun invoke(args: A): T
}