package com.visma.data.features.photocapture.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val filename: String,
    val path: String,
    val timestamp: Long
)