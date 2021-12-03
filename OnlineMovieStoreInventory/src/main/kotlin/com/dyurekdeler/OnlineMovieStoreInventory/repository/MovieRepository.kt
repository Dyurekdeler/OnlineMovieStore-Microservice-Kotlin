package com.dyurekdeler.OnlineMovieStoreInventory.repository

import com.dyurekdeler.OnlineMovieStoreInventory.entity.Movie
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface MovieRepository: MongoRepository<Movie, String> {
    fun findOneById(id: ObjectId): Movie
    override fun deleteAll()
}