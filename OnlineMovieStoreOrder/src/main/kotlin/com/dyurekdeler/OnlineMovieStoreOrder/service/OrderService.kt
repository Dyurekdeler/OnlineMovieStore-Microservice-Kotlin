package com.dyurekdeler.OnlineMovieStoreOrder.service

import com.dyurekdeler.OnlineMovieStoreOrder.entity.Order
import com.dyurekdeler.OnlineMovieStoreOrder.repository.OrderRepository
import com.dyurekdeler.OnlineMovieStoreOrder.request.OrderRequest

class OrderService(
    private val orderRepository: OrderRepository
) {

    fun placeOrder(request: OrderRequest): Order{
        // validate user

        // validate quantity

        // get payment

        // insert order
        val order = orderRepository.save(
            Order(
                movieId = request.movieId,
                customerId = request.customerId,
                quantity = request.quantity
            )
        )

        // update quantity

        //start delivery
        return order
    }

    fun cancelOrder() {
        // delete/update order

        // update quantity
    }
}