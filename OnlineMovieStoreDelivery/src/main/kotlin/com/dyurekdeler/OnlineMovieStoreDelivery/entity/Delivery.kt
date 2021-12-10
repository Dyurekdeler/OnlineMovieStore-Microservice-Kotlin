package com.dyurekdeler.OnlineMovieStoreInventory.entity

import com.dyurekdeler.OnlineMovieStoreDelivery.model.DeliveryStatus
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class Delivery (
    @Id
    val id: ObjectId = ObjectId.get(),
    val orderId: ObjectId,
    val status: DeliveryStatus,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)