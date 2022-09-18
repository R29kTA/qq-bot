package io.github.r29kta.qqbot.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class MessageRoutes {
    @Bean
    fun messageRoute(handler: MessageHandler) = coRouter {
        POST("/msg",handler::sendMessage)
    }
}