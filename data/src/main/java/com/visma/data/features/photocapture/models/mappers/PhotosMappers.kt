package com.visma.data.features.photocapture.models.mappers


import com.visma.data.features.photocapture.models.entities.PhotoEntity
import com.visma.domain.features.photocapture.models.Photo
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