package com.dyurekdeler.OnlineMovieStoreOrder.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class Delivery (
    val id: ObjectId,
    val orderId: ObjectId,
    val status: DeliveryStatus,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)