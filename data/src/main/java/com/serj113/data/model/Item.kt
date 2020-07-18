package com.serj113.data.model

import com.serj113.domain.entity.User
import com.squareup.moshi.Json

data class Item(
    @field:Json(name = "avatar_url")
    var avatarUrl: String,
    @field:Json(name = "id")
    var id: Int,
    @field:Json(name = "login")
    var login: String,
    @field:Json(name = "url")
    var url: String
)

fun Item.toUserEntity() = User(id, login, avatarUrl, url)

fun List<Item>.toUserEntities() = map { it.toUserEntity() }