package com.dyurekdeler.OnlineMovieStoreInventory.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class Movie (
    @Id
    val id: ObjectId = ObjectId.get(),
    val title: String,
    val duration: Int,
    val about: String,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)