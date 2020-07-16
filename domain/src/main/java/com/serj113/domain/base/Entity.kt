package com.serj113.domain.base

data class Entity<T>(val data: T?, val message: String?, val status: NetworkState) {
    companion object {

        fun <T> success(data: T?): Entity<T> {
            return Entity(data, null, NetworkState.SUCCESS)
        }

        fun <T> error(message: String): Entity<T> {
            return Entity(null, message, NetworkState.ERROR)
        }

        fun <T> loading(): Entity<T> {
            return Entity(null, null, NetworkState.LOADING)
        }
    }
}