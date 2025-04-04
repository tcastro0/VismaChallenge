package com.visma.domain.features.expenses.models

enum class VismaCurrency(private val currency: String,private val symbol: String) {
    EUR("EUR", "€"),
    USD("USD", "$"),
    JPY("JPY", "¥"),
    OTHER("OTHER", "");

    fun getSymbol(): String {
        return symbol
    }

    fun getCurrency(): String {
        return currency
    }
    companion object {
        fun fromCurrency(currency: String): VismaCurrency {
            return entries.find { it.currency == currency } ?: OTHER
        }
    }
}