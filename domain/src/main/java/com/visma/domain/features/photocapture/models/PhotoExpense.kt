package com.visma.domain.features.photocapture.models

data class PhotoExpense (
    val id: String,
    val expenseId: String,
    val photoId: String
){
    constructor(expenseId: String, photoId: String) : this("$expenseId|$photoId", expenseId, photoId)
}

