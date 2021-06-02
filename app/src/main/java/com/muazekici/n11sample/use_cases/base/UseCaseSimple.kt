package com.muazekici.n11sample.use_cases.base

import kotlinx.coroutines.flow.flow

abstract class UseCaseSimple<in P, R> : UseCase<P, R>() {

    @Throws(RuntimeException::class)
    abstract suspend fun execution(parameter: P): R

    open override fun getResultFlow(parameter: P) = flow {
        emit(execution(parameter))
    }

}