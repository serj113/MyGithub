package com.serj113.domain.base

import kotlinx.coroutines.flow.StateFlow

abstract class StateFlowUseCase<A, T: Any> :
    BaseUseCase<A, StateFlow<T>>