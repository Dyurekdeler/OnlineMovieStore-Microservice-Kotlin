package com.dyurekdeler.OnlineMovieStoreOrder.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class Payment (
    val id: ObjectId,
    val orderId: ObjectId,
    val paymentMethod: PaymentMethod,
    var isCancelled: Boolean,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)
