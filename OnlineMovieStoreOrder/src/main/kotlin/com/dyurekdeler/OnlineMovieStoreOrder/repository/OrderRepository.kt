package com.dyurekdeler.OnlineMovieStoreOrder.repository

import com.dyurekdeler.OnlineMovieStoreOrder.entity.Order
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: MongoRepository<Order, String> {
    fun findOneById(id: ObjectId): Order
    override fun deleteAll()
}