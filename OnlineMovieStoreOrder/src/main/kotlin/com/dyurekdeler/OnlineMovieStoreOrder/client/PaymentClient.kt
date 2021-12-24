package com.dyurekdeler.OnlineMovieStoreOrder.client

import com.dyurekdeler.OnlineMovieStoreCustomer.request.PaymentRequest
import com.dyurekdeler.OnlineMovieStoreOrder.model.Payment
import org.bson.types.ObjectId
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping

@FeignClient(name = "OnlineMovieStorePayment", url = "\${OnlineMovieStore.server.payment.url}")
interface PaymentClient {

    @PostMapping("\${OnlineMovieStore.server.payment.ws.processPayment}")
    fun processPayment(request: PaymentRequest): Payment

    /*
    @PutMapping("\${OnlineMovieStore.server.payment.ws.updatePayment}")
    fun updatePayment(payment: Payment): Payment

     */

    @PostMapping("\${OnlineMovieStore.server.payment.ws.cancelPayment}")
    fun cancelPayment(payment: Payment)
}