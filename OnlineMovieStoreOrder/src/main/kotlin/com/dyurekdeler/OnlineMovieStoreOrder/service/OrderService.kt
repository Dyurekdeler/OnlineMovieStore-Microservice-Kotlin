package com.dyurekdeler.OnlineMovieStoreOrder.service

import com.dyurekdeler.OnlineMovieStoreCustomer.request.PaymentRequest
import com.dyurekdeler.OnlineMovieStoreInventory.request.DeliveryRequest
import com.dyurekdeler.OnlineMovieStoreOrder.client.CustomerClient
import com.dyurekdeler.OnlineMovieStoreOrder.client.DeliveryClient
import com.dyurekdeler.OnlineMovieStoreOrder.client.InventoryClient
import com.dyurekdeler.OnlineMovieStoreOrder.client.PaymentClient
import com.dyurekdeler.OnlineMovieStoreOrder.entity.Order
import com.dyurekdeler.OnlineMovieStoreOrder.model.Delivery
import com.dyurekdeler.OnlineMovieStoreOrder.model.DeliveryStatus
import com.dyurekdeler.OnlineMovieStoreOrder.model.Payment
import com.dyurekdeler.OnlineMovieStoreOrder.repository.OrderRepository
import com.dyurekdeler.OnlineMovieStoreOrder.request.OrderRequest
import org.bson.types.ObjectId
import java.time.LocalDateTime

class OrderService(
    private val orderRepository: OrderRepository,
    private val customerClient: CustomerClient,
    private val inventoryClient: InventoryClient,
    private val paymentClient: PaymentClient,
    private val deliveryClient: DeliveryClient

) {

    fun placeOrder(request: OrderRequest): Order?{
        // validate user
        customerClient.getCustomer(request.customerId)?.let {

            // validate quantity
            val movie = inventoryClient.getMovieById(request.movieId)
            if (movie.quantity < request.quantity) throw Exception("Movie has not enough quantity")

            // insert order
            var order = Order(
                movieId = request.movieId,
                customerId = request.customerId,
                quantity = request.quantity,
                isCanceled = false
            )
            orderRepository.save(order)

            // get payment
            var payment: Payment
            try {
                val paymentRequest = PaymentRequest(
                    orderId = order.id,
                    request.paymentMethod,
                    false
                )
                payment = paymentClient.processPayment(paymentRequest)
            } catch (e: Exception) {
                cancelOrder(order)
                throw Exception("Payment process failed")
            }


            // update quantity
            try {
                val newQuantity = movie.quantity - request.quantity
                inventoryClient.updateMovie(movie.id, newQuantity)
            } catch (e: Exception) {
                // todo: cancel paymentı put request ile yapılabilir mi?
                paymentClient.cancelPayment(payment)
                cancelOrder(order)
                throw Exception("Update inventory process failed")
            }

            // start delivery
            val delivery: Delivery
            try {
                val deliveryRequest = DeliveryRequest(
                    order.id,
                    DeliveryStatus.Preparing
                )
                delivery = deliveryClient.processDelivery(deliveryRequest)
            } catch (e: Exception) {
                val oldQuantity = movie.quantity + request.quantity
                inventoryClient.updateMovie(movie.id, oldQuantity)
                paymentClient.cancelPayment(payment)
                cancelOrder(order)
                throw Exception("Start delivery failed")
            }

            return order

        } ?: throw Exception("Customer not found")

        // todo: how to cancel delivery, if sth happens?
    }

    fun cancelOrder(order: Order): Order {
        order.isCanceled = true
        orderRepository.save(order)
        return order
    }


}