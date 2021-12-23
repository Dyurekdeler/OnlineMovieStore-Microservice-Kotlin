package com.dyurekdeler.OnlineMovieStoreInventory.request

import com.dyurekdeler.OnlineMovieStoreOrder.model.DeliveryStatus
import org.bson.types.ObjectId

class DeliveryRequest (
    val orderId: ObjectId,
    val status: DeliveryStatus
)