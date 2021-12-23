package com.dyurekdeler.OnlineMovieStoreOrder.client

import com.dyurekdeler.OnlineMovieStoreOrder.model.Movie
import com.dyurekdeler.OnlineMovieStoreOrder.request.MovieRequest
import org.bson.types.ObjectId
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.*

@FeignClient("OnlineMovieStoreInventory", url = "\${OnlineMovieStore.server.inventory.url}")
interface InventoryClient {

    @GetMapping("\${OnlineMovieStore.server.inventory.ws.getMovie}")
    fun getMovie(@PathVariable id: ObjectId): Movie

    @PutMapping("\${onlineMovieStore.server.inventory.ws.updateMovie}")
    fun updateMovie(@PathVariable id: ObjectId, movieRequest: MovieRequest): Movie

}