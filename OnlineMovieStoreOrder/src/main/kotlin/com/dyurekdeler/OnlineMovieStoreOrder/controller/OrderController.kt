package com.dyurekdeler.OnlineMovieStoreOrder.controller

import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

class OrderController(
    private val orderRepository: OrderRepository
) {
    @GetMapping()
    fun getAllOrders(): ResponseEntity<List<Order>> {
        val orders = orderRepository.findAll()
        return ResponseEntity.ok(orders)
    }

    @GetMapping("/{id}")
    fun getOrder(@PathVariable("id") id: String): ResponseEntity<Order> {
        val order = orderRepository.findOneById(ObjectId(id))
        return ResponseEntity.ok(order)
    }

    @PostMapping
    fun createOrder(@RequestBody request: OrderRequest): ResponseEntity<Order> {
        val order = orderRepository.save(
            Order(
                movieId = request.movieId,
                customerId = request.customerId,
            )
        )
        return ResponseEntity(order, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateOrder(@RequestBody request: OrderRequest, @PathVariable("id") id: String): ResponseEntity<Order> {
        val order = orderRepository.findOneById(ObjectId(id))
        val updatedOrder = orderRepository.save(
            Order(
                id = order.id,
                movieId = request.movieId,
                customerId = request.customerId,
                createdDate = order.createdDate,
                modifiedDate = LocalDateTime.now()
            )
        )
        return ResponseEntity.ok(updatedOrder)
    }

    @DeleteMapping("/{id}")
    fun deleteOrder(@PathVariable("id") id: String): ResponseEntity<Unit> {
        orderRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }


    fun cancelOrder(orderId: Long) {
        // todo: cancel order
        // update order status to canceled
    }
}