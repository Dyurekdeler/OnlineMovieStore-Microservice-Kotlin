package com.dyurekdeler.OnlineMovieStoreCustomer.entity

import org.springframework.data.annotation.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Customer (
    @Id
    val id: ObjectId = ObjectId.get(),
    val firstname: String,
    val lastname: String,
    val address: String,
    val phone: String,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)