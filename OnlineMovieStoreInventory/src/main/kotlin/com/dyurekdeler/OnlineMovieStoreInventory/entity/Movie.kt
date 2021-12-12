package com.dyurekdeler.OnlineMovieStoreInventory.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Movie (
    @Id
    val id: ObjectId = ObjectId.get(),
    val title: String,
    val duration: Int,
    val about: String,
    val quantity: Int,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)