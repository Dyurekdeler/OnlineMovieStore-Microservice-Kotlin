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
import com.dyurekdeler.OnlineMovieStoreOrder.model.PaymentMethod
import com.dyurekdeler.OnlineMovieStoreOrder.repository.OrderRepository
import com.dyurekdeler.OnlineMovieStoreOrder.request.MovieRequest
import com.dyurekdeler.OnlineMovieStoreOrder.request.OrderRequest
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

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
            logger.info(">>Validating quantity. Movie: ${movie.quantity}")
            if (movie.quantity < request.quantity) throw Exception("Movie has not enough quantity")


            // insert order
            val order = Order(
                movieId = request.movieId,
                customerId = request.customerId,
                quantity = request.quantity,
                isCanceled = false
            )
            orderRepository.save(order)
            logger.info(">>Inserting order. Order: $order")

            // get payment
            val payment: Payment
            try {
                val paymentRequest = PaymentRequest(
                    orderId = order.id,
                    request.paymentMethod,
                    false
                )
                payment = paymentClient.processPayment(paymentRequest)
                logger.info("Processing payment. Payment: $payment")
            } catch (e: Exception) {
                cancelOrder(order)
                logger.error(e.toString())
                throw Exception("Payment process failed")
            }


            // update quantity
            try {
                val newQuantity = movie.quantity - request.quantity
                movie.quantity = newQuantity
                inventoryClient.changeQuantity(movie)
                logger.info("Inventory updating. Movie qu: ${movie.quantity}")
            } catch (e: Exception) {
                logger.error(e.toString())
                payment.isCancelled = true
                paymentClient.cancelPayment(payment)
                cancelOrder(order)
                throw Exception("Update inventory process failed")
            }

            // start delivery
            try {
                val deliveryRequest = DeliveryRequest(
                    order.id,
                    DeliveryStatus.Preparing
                )
                deliveryClient.processDelivery(deliveryRequest)
                logger.info("Processing delivery.")
            } catch (e: Exception) {
                logger.error(e.toString())
                logger.info("deniz")
                val oldQuantity = movie.quantity + request.quantity
                movie.quantity = oldQuantity
                inventoryClient.changeQuantity(movie)
                logger.info("change quantity worked")
                payment.isCancelled = true
                paymentClient.cancelPayment(payment)
                logger.info("cancel payment worked")
                cancelOrder(order)
                logger.info("cancel order worked")
                throw Exception("Start delivery failed")
            }

            return order

        } ?: throw Exception("Customer not found")

    }

    fun cancelOrder(order: Order): Order {
        order.isCanceled = true
        orderRepository.save(order)
        return order
    }


}