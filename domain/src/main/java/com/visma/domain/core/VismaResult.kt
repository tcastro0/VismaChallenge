package com.visma.domain.core

sealed class VismaResult<out T> {
    data class Success<out T>(val result: T) : VismaResult<T>()
    data class Error(val message: String, val cause: Exception? = null) : VismaResult<Nothing>()
}