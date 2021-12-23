package com.dyurekdeler.OnlineMovieStoreOrder.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class Movie (
    val id: ObjectId,
    val title: String,
    val duration: Int,
    val about: String,
    val quantity: Int,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)