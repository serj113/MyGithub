package com.serj113.data.model

import com.squareup.moshi.Json

data class Response (
    @Json(name = "incomplete_results")
    var incompleteResults: Boolean,
    @Json(name = "items")
    var items: List<Item>,
    @Json(name = "total_count")
    var totalCount: Int
)