package com.dyurekdeler.OnlineDeliveryStoreInventory.controller

import com.dyurekdeler.OnlineMovieStoreInventory.entity.Delivery
import com.dyurekdeler.OnlineMovieStoreInventory.repository.DeliveryRepository
import com.dyurekdeler.OnlineMovieStoreInventory.request.DeliveryRequest
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/deliveries")
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

    @PostMapping
    fun createDelivery(@RequestBody request: DeliveryRequest): ResponseEntity<Delivery> {
        val delivery = deliveryRepository.save(Delivery(
            orderId = request.orderId,
            status = request.status,
        ))
        return ResponseEntity(delivery, HttpStatus.CREATED)
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