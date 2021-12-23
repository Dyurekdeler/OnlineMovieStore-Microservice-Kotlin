package com.dyurekdeler.OnlineMovieStoreOrder.client

import com.dyurekdeler.OnlineMovieStoreOrder.model.Customer
import com.dyurekdeler.OnlineMovieStoreOrder.model.Movie
import org.bson.types.ObjectId
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMethod
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@FeignClient("OnlineMovieStoreInventory", url = "\${onlineMovieStore.inventory.url}")
interface InventoryClient {

    @GetMapping("/movies/{id}")
    fun getMovie(id: ObjectId): Movie

    @PutMapping("/movies/{id}")
    fun updateMovie(id: ObjectId, quantity: Int): Movie

}