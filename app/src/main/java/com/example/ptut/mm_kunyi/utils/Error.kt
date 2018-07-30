package com.example.ptut.mm_kunyi.utils

sealed class Error
data class EmptyError(val msg: String): Error()
data class NetworkError(val msg: String): Error()
data class DatabaseError(val msg: String): Error()