package com.dyurekdeler.OnlineMovieStoreInventory.request

import com.dyurekdeler.OnlineMovieStoreInventory.entity.Movie
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

class MovieRequest (
    val title: String,
    val duration: Int,
    val about: String,
    val quantity: Int
)