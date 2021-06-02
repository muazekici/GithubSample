package com.muazekici.n11sample.use_cases.base

import kotlinx.coroutines.flow.*

abstract class UseCase<in P, R> {

    open abstract fun getResultFlow(parameter: P): Flow<R>

    operator fun invoke(parameter: P): Flow<UseCaseResult<R>> {
        return (getResultFlow(parameter)
            .map {
                UseCaseResult.Success(it)
            } as Flow<UseCaseResult<R>>)
            .onStart {
                emit(UseCaseResult.Loading)
            }
            .catch { cause ->
                emit(UseCaseResult.Error(cause))
            }
    }

    suspend operator fun invoke(parameter: P, resultFlow:MutableSharedFlow<UseCaseResult<R>>) {
        (getResultFlow(parameter)
            .map {
                UseCaseResult.Success(it)
            } as Flow<UseCaseResult<R>>)
            .onStart {
                emit(UseCaseResult.Loading)
            }
            .catch { cause ->
                emit(UseCaseResult.Error(cause))
            }.collect {
                resultFlow.emit(it)
            }
    }

}