package dev.codingstoic.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync // Enables the running of code asynchronously
class ServerApplication

fun main(args: Array<String>) {
	runApplication<ServerApplication>(*args)
}
