package com.visma.data.features.photocapture.models.mappers


import com.visma.data.features.photocapture.models.entities.PhotoEntity
import com.visma.data.features.photocapture.models.entities.PhotoExpenseEntity
import com.visma.domain.features.photocapture.models.Photo
import com.visma.domain.features.photocapture.models.PhotoExpense
import java.time.LocalDateTime
import java.time.ZoneOffset


fun PhotoEntity.toDomain(): Photo {
    return Photo(
        id = this.id,
        filename = this.filename,
        path = this.path,
    )
}

fun Photo.toEntity(): PhotoEntity {
    return PhotoEntity(
        id = this.id,
        filename = this.filename,
        path = this.path,
        timestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()
    )
}


fun PhotoExpenseEntity.toDomain(): PhotoExpense {
    return PhotoExpense(
        expenseId = this.expenseID,
        photoId = this.photoID,
    )
}

fun PhotoExpense.toEntity(): PhotoExpenseEntity {
    return PhotoExpenseEntity(
        expenseID = this.expenseId,
        photoID = this.photoId,
    )
}