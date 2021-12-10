package com.dyurekdeler.OnlineMovieStoreCustomer.entity

import com.dyurekdeler.OnlineMovieStorePayment.model.PaymentMethod
import org.springframework.data.annotation.Id
import org.bson.types.ObjectId
import java.time.LocalDateTime

data class Payment (
    @Id
    val id: ObjectId = ObjectId.get(),
    val orderId: ObjectId,
    val paymentMethod: PaymentMethod,
    val isCancelled: Boolean,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)