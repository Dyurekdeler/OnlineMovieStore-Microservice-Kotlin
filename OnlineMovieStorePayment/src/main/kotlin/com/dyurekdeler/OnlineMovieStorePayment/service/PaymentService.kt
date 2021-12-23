package com.dyurekdeler.OnlineMovieStorePayment.service

import com.dyurekdeler.OnlineMovieStoreCustomer.entity.Payment
import com.dyurekdeler.OnlineMovieStoreCustomer.repository.PaymentRepository
import com.dyurekdeler.OnlineMovieStoreCustomer.request.PaymentRequest
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class PaymentService(
    private val paymentRepository: PaymentRepository
) {

    fun processPayment(request: PaymentRequest): Payment {
        // save payment info
        val payment = paymentRepository.save(
            Payment(
                orderId = request.orderId,
                paymentMethod = request.paymentMethod,
                isCancelled = request.isCancelled
            )
        )
        return payment
    }


    fun kafkaListener() {
        // orderdan bir mesak geldi
        // procees metodunu çalıştırdı
    }

    fun processCheorogyPayment(request: PaymentRequest) {
        // try
            // logic işlem
        // catch
            // hata topic'ine msj at

        // inventory topic msj inventory devam etsin
    }

    fun cancelPayment(paymentId: ObjectId): Payment {
        var payment = paymentRepository.findOneById(paymentId)
        payment.isCancelled = true
        paymentRepository.save(payment)
        return payment
    }

}