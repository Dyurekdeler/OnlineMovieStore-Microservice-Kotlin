package com.dyurekdeler.OnlineMovieStoreOrder.service

import com.dyurekdeler.OnlineMovieStoreCustomer.request.PaymentRequest
import com.dyurekdeler.OnlineMovieStoreInventory.request.DeliveryRequest
import com.dyurekdeler.OnlineMovieStoreOrder.client.CustomerClient
import com.dyurekdeler.OnlineMovieStoreOrder.client.DeliveryClient
import com.dyurekdeler.OnlineMovieStoreOrder.client.InventoryClient
import com.dyurekdeler.OnlineMovieStoreOrder.client.PaymentClient
import com.dyurekdeler.OnlineMovieStoreOrder.entity.Order
import com.dyurekdeler.OnlineMovieStoreOrder.model.*
import com.dyurekdeler.OnlineMovieStoreOrder.repository.OrderRepository
import com.dyurekdeler.OnlineMovieStoreOrder.request.OrderRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val customerClient: CustomerClient,
    private val inventoryClient: InventoryClient,
    private val paymentClient: PaymentClient,
    private val deliveryClient: DeliveryClient

) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun placeOrder(request: OrderRequest): Order{
        // validate user
        customerClient.getCustomer(request.customerId.toString())?.let {
            // validate quantity
            val movie = inventoryClient.getMovie(request.movieId)
            if (movie.quantity < request.quantity) throw Exception("Movie has not enough quantity!")


            // insert order
            val order = Order(
                movieId = request.movieId,
                customerId = request.customerId,
                quantity = request.quantity,
                isCanceled = false
            )
            orderRepository.save(order)
            logger.info(">> Inserting order. Order: $order")

            // get payment
            val payment: Payment
            try {
                val paymentRequest = PaymentRequest(
                    orderId = order.id,
                    request.paymentMethod,
                    false
                )
                payment = paymentClient.processPayment(paymentRequest)
                logger.info(">> Processing payment. Payment: $payment")
            } catch (e: Exception) {
                cancelOrder(order)
                logger.error(e.toString())
                throw Exception("Payment process failed!")
            }


            // update quantity
            try {
                logger.info(">> Movie quantity before order processing update. Movie: ${movie.title}, Quantity: ${movie.quantity}")
                updateInventory(movie, request.quantity, false)
            } catch (e: Exception) {
                logger.error(e.toString())
                cancelPayment(payment)
                cancelOrder(order)
                throw Exception("Update inventory process failed!")
            }

            // start delivery
            try {
                val deliveryRequest = DeliveryRequest(
                    order.id,
                    DeliveryStatus.Preparing
                )
                deliveryClient.processDelivery(deliveryRequest)
                logger.info(">> Processing delivery")
            } catch (e: Exception) {
                logger.error(e.toString())
                logger.info(">> Movie quantity before cancellation update. Movie: ${movie.title}, Quantity: ${movie.quantity}")
                updateInventory(movie, request.quantity, true)
                cancelPayment(payment)
                cancelOrder(order)
                throw Exception("Start delivery failed!")
            }

            return order

        } ?: throw Exception("Customer not found!")

    }

    fun cancelOrder(order: Order): Order {
        order.isCanceled = true
        orderRepository.save(order)
        logger.info(">>>> Order cancelled. Order: $order")
        return order
    }

    fun cancelPayment(payment: Payment): Payment {
        payment.isCancelled = true
        paymentClient.cancelPayment(payment)
        logger.info(">>>> Payment cancelled. Payment: $payment")
        return payment
    }

    fun updateInventory(movie: Movie, quantity: Int, isIncrease: Boolean) {
        val updatedQuantity: Int
        if (isIncrease)
            updatedQuantity = movie.quantity + quantity
        else
            updatedQuantity = movie.quantity - quantity
        movie.quantity = updatedQuantity
        inventoryClient.changeQuantity(movie)
        logger.info(">>> Updated movie quantity. Movie: ${movie.title}, Quantity: ${movie.quantity}")
    }


}