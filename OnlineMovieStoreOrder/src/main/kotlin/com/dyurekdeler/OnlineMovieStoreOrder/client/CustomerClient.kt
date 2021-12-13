package com.dyurekdeler.OnlineMovieStoreOrder.client

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient("OnlineMovieStoreCustomer")
interface CustomerClient {
    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "api/check")
    fun getAllUsers(): String
}