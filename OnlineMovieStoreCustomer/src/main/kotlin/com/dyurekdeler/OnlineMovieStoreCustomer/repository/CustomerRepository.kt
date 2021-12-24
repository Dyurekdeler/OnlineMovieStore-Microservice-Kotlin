package com.dyurekdeler.OnlineMovieStoreCustomer.repository

import com.dyurekdeler.OnlineMovieStoreCustomer.entity.Customer
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: MongoRepository<Customer, String> {
    fun findOneById(id: ObjectId): Customer
    override fun deleteAll()
}