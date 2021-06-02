package com.muazekici.n11sample.ui.exts

import com.muazekici.n11sample.use_cases.base.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map


fun Flow<UseCaseResult<*>>.toLoadFlow() =
    this.map { it is UseCaseResult.Loading }

fun Flow<UseCaseResult<*>>.toErrorFlow() =
    this.filter { it is UseCaseResult.Error }.map { (it as UseCaseResult.Error).throwable }
