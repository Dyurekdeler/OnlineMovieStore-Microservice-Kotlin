package com.dyurekdeler.OnlineMovieStoreOrder.client

import com.dyurekdeler.OnlineMovieStoreCustomer.request.PaymentRequest
import com.dyurekdeler.OnlineMovieStoreOrder.model.Payment
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping

@FeignClient("OnlineMovieStorePayment", url = "\${onlineMovieStore.payment.url}")
interface PaymentClient {

    @PostMapping("/payments/processPayment")
    fun processPayment(request: PaymentRequest): Payment

    @PutMapping("/payments/{id}")
    fun cancelPayment(payment: Payment): Payment
}