package com.dyurekdeler.OnlineMovieStoreCustomer.entity

import org.springframework.data.annotation.Id
import org.bson.types.ObjectId
import java.time.LocalDateTime

data class Customer (
    @Id
    val id: ObjectId = ObjectId.get(),
    val name: String,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)