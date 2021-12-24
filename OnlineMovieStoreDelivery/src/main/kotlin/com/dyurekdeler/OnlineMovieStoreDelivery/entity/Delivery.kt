package com.dyurekdeler.OnlineMovieStoreDelivery.entity

import com.dyurekdeler.OnlineMovieStoreDelivery.model.DeliveryStatus
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Delivery (
    @Id
    val id: ObjectId = ObjectId.get(),
    val orderId: ObjectId,
    val status: DeliveryStatus,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)