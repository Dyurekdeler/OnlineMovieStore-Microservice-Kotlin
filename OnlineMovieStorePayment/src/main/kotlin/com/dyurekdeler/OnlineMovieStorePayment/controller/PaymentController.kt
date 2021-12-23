package com.dyurekdeler.OnlineMovieStorePayment.controller

import com.dyurekdeler.OnlineMovieStoreCustomer.entity.Payment
import com.dyurekdeler.OnlineMovieStoreCustomer.repository.PaymentRepository
import com.dyurekdeler.OnlineMovieStoreCustomer.request.PaymentRequest
import com.dyurekdeler.OnlineMovieStorePayment.service.PaymentService
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/payments")
class PaymentController(
    private val paymentRepository: PaymentRepository,
    private val paymentService: PaymentService
) {

    @GetMapping()
    fun getAllPayments(): ResponseEntity<List<Payment>> {
        val payments = paymentRepository.findAll()
        return ResponseEntity.ok(payments)
    }

    @GetMapping("/{id}")
    fun getPayment(@PathVariable("id") id: String): ResponseEntity<Payment> {
        val payment = paymentRepository.findOneById(ObjectId(id))
        return ResponseEntity.ok(payment)
    }

    @PostMapping("/processPayment")
    fun processPayment(@RequestBody request: PaymentRequest): ResponseEntity<Payment> {
        val payment = paymentService.processPayment(request)
        return ResponseEntity(payment, HttpStatus.CREATED)
    }

    @PostMapping("/cancelPayment")
    fun cancelPayment(@RequestBody payment: Payment): ResponseEntity<Payment> {
        payment.isCancelled = true
        paymentRepository.save(payment)
        return ResponseEntity(payment, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updatePayment(@RequestBody request: PaymentRequest, @PathVariable("id") id: String): ResponseEntity<Payment> {
        val payment = paymentRepository.findOneById(ObjectId(id))
        val updatedPayment = paymentRepository.save(
            Payment(
                id = payment.id,
                orderId = request.orderId,
                paymentMethod = request.paymentMethod,
                isCancelled = request.isCancelled,
                createdDate = payment.createdDate,
                modifiedDate = LocalDateTime.now()
            )
        )
        return ResponseEntity.ok(updatedPayment)
    }

    @DeleteMapping("/{id}")
    fun deletePayment(@PathVariable("id") id: String): ResponseEntity<Unit> {
        paymentRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}