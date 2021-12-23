package com.dyurekdeler.OnlineMovieStoreOrder.client

import com.dyurekdeler.OnlineMovieStoreOrder.model.Customer
import org.bson.types.ObjectId
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient("OnlineMovieStoreCustomer", url = "\${OnlineMovieStore.server.customer.url}")
interface CustomerClient {

    @GetMapping("\${OnlineMovieStore.server.customer.ws.getCustomer}")
    fun getCustomer(@PathVariable id: ObjectId): Customer?
}