package com.dyurekdeler.OnlineMovieStoreOrder.request

import org.bson.types.ObjectId

class OrderRequest (
    val movieId: ObjectId,
    val customerId: ObjectId,
    val quantity: Int,
)