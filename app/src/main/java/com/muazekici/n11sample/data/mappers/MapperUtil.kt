package com.muazekici.n11sample.data.mappers

interface ObjMapper<I, O> {
    fun map(input: I): O
}

interface ObjListMapper<I, O> : ObjMapper<List<I>, List<O>>

open class ListMapperImpl<I, O>(private val mapper: ObjMapper<I, O>) :
    ObjListMapper<I, O> {
    override fun map(input: List<I>): List<O> {
        return input.map { mapper.map(it) }
    }
}