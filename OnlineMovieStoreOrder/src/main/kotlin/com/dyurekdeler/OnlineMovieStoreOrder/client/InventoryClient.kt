package com.dyurekdeler.OnlineMovieStoreOrder.client

import com.dyurekdeler.OnlineMovieStoreOrder.model.Movie
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*

@FeignClient(name = "OnlineMovieStoreInventory", url = "\${OnlineMovieStore.server.inventory.url}")
interface InventoryClient {

    @GetMapping("\${OnlineMovieStore.server.inventory.ws.getMovie}")
    fun getMovie(@PathVariable id: String): Movie

    /*
    @PutMapping("\${OnlineMovieStore.server.inventory.ws.updateMovie}")
    fun updateMovie(movie: Movie): Movie

     */

    @PostMapping("\${OnlineMovieStore.server.inventory.ws.changeQuantity}")
    fun changeQuantity(@RequestBody movie: Movie)

}