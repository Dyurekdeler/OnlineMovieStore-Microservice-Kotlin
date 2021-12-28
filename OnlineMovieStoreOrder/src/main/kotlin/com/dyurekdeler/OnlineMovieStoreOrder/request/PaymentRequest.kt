package com.dyurekdeler.OnlineMovieStoreCustomer.request

import com.dyurekdeler.OnlineMovieStoreOrder.model.PaymentMethod
import org.bson.types.ObjectId

class PaymentRequest (
    val orderId: String,
    val paymentMethod: PaymentMethod,
    val isCancelled: Boolean,
)