package com.dyurekdeler.OnlineMovieStoreInventory.repository

import com.dyurekdeler.OnlineMovieStoreInventory.entity.Delivery
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface DeliveryRepository: MongoRepository<Delivery, String> {
    fun findOneById(id: ObjectId): Delivery
    override fun deleteAll()
}