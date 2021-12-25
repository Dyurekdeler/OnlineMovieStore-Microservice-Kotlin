package com.dyurekdeler.OnlineMovieStoreDelivery.controller

import com.dyurekdeler.OnlineMovieStoreDelivery.entity.Delivery
import com.dyurekdeler.OnlineMovieStoreDelivery.repository.DeliveryRepository
import com.dyurekdeler.OnlineMovieStoreDelivery.request.DeliveryRequest
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
class DeliveryController(
    private val deliveryRepository: DeliveryRepository
) {
    @GetMapping()
    fun getAllDeliveries(): ResponseEntity<List<Delivery>> {
        val deliverys = deliveryRepository.findAll()
        return ResponseEntity.ok(deliverys)
    }

    @GetMapping("/{id}")
    fun getDelivery(@PathVariable("id") id: String): ResponseEntity<Delivery> {
        val delivery = deliveryRepository.findOneById(ObjectId(id))
        return ResponseEntity.ok(delivery)
    }

    @PostMapping("/processDelivery")
    fun processDelivery(@RequestBody request: DeliveryRequest): Delivery {
        val delivery = deliveryRepository.save(Delivery(
            orderId = request.orderId,
            status = request.status,
        ))
        // throw Exception("BAD REQUEST ON PURPOSE FOR ROLLBACK SCENARIO")
        return delivery
    }

    @PutMapping("/{id}")
    fun updateDelivery(@RequestBody request: DeliveryRequest, @PathVariable("id") id: String): ResponseEntity<Delivery> {
        val delivery = deliveryRepository.findOneById(ObjectId(id))
        val updatedDelivery = deliveryRepository.save(Delivery(
            id = delivery.id,
            orderId = request.orderId,
            status = request.status,
            createdDate = delivery.createdDate,
            modifiedDate = LocalDateTime.now()
        ))
        return ResponseEntity.ok(updatedDelivery)
    }

    @DeleteMapping("/{id}")
    fun deleteDelivery(@PathVariable("id") id: String): ResponseEntity<Unit> {
        deliveryRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }

}