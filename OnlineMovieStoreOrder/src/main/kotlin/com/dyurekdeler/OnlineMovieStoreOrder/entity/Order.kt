package com.dyurekdeler.OnlineMovieStoreOrder.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class Order(
    @Id
    val id: ObjectId = ObjectId.get(),
    val customerId: ObjectId,
    val movieId: ObjectId,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)