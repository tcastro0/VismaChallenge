package com.visma.domain.core

sealed class VismaResult {
    object Success : VismaResult()
    data class Error(val message: String, val cause: Exception? = null) : VismaResult()
}