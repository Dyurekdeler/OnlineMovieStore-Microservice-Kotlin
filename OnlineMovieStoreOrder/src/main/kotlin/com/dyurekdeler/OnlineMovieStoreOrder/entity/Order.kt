package com.dyurekdeler.OnlineMovieStoreOrder.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Order(
    @Id
    val id: ObjectId = ObjectId.get(),
    val customerId: ObjectId,
    val movieId: ObjectId,
    val quantity: Int,
    var isCanceled: Boolean = false,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)