package com.serj113.presentation.base

import androidx.paging.PageKeyedDataSource

abstract class BaseDataSource<Key, Value> : PageKeyedDataSource<Key, Value>() {
    protected var page: Long = 1L
}