package com.dyurekdeler.OnlineMovieStoreOrder.model

import org.bson.types.ObjectId
import java.time.LocalDateTime

data class Customer(
    val id: ObjectId,
    val firstname: String,
    val lastname: String,
    val address: String,
    val phone: String,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)
