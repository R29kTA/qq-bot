package io.github.r29kta.qqbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
class QqBotApplication

fun main(args: Array<String>) {
    runApplication<QqBotApplication>(*args)
}
