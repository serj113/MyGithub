package com.serj113.data.model

import com.squareup.moshi.Json

data class UserList (
    @field:Json(name = "incomplete_results")
    var incompleteResults: Boolean,
    @field:Json(name = "items")
    var items: List<Item>,
    @field:Json(name = "total_count")
    var totalCount: Int
)