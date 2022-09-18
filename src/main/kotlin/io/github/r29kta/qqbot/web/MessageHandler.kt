package io.github.r29kta.qqbot.web

import io.github.r29kta.qqbot.bot.models.Msg
import io.github.r29kta.qqbot.bot.service.MessageService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*


@Component
class MessageHandler(private val service: MessageService) {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    suspend fun sendMessage(request: ServerRequest): ServerResponse{
        val msg = request.awaitBody<Msg>()
        val msgRec = service.sendGroupMessage(msg)
        service.saveGroupMessage(msgRec.source,msg.publisher)
        return ServerResponse.ok().json().bodyValueAndAwait("success")
    }
}