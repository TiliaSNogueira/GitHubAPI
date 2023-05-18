package com.example.github.ui

interface Mapper<E, T> {
    fun mapFrom(from: E): T
}