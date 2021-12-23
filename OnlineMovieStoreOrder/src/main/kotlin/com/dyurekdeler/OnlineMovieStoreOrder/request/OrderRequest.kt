package com.dyurekdeler.OnlineMovieStoreOrder.request

import com.dyurekdeler.OnlineMovieStoreOrder.model.PaymentMethod
import org.bson.types.ObjectId

class OrderRequest (
    val movieId: ObjectId,
    val customerId: ObjectId,
    val quantity: Int,
    val paymentMethod: PaymentMethod
)