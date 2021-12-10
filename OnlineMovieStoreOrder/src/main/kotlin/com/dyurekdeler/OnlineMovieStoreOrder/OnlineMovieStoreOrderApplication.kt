package com.dyurekdeler.OnlineMovieStoreOrder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class OnlineMovieStoreOrderApplication

fun main(args: Array<String>) {
	runApplication<OnlineMovieStoreOrderApplication>(*args)
}
