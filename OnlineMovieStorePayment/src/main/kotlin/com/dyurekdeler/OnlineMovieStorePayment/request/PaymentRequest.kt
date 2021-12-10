package com.dyurekdeler.OnlineMovieStoreCustomer.request

import com.dyurekdeler.OnlineMovieStorePayment.model.PaymentMethod
import org.bson.types.ObjectId

class PaymentRequest (
    val orderId: ObjectId,
    val paymentMethod: PaymentMethod,
    val isCancelled: Boolean,
)