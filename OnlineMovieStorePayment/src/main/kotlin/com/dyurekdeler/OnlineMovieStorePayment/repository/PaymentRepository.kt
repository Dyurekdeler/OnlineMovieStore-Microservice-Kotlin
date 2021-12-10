package com.dyurekdeler.OnlineMovieStoreCustomer.repository

import com.dyurekdeler.OnlineMovieStoreCustomer.entity.Payment
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PaymentRepository: MongoRepository<Payment, String> {
    fun findOneById(id: ObjectId): Payment
    override fun deleteAll()
}