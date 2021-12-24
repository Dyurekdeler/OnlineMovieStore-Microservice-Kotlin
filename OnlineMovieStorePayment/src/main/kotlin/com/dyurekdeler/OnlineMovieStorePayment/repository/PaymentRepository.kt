package com.dyurekdeler.OnlineMovieStorePayment.repository

import com.dyurekdeler.OnlineMovieStoreCustomer.entity.Payment
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository: MongoRepository<Payment, String> {
    fun findOneById(id: ObjectId): Payment
    override fun deleteAll()
}