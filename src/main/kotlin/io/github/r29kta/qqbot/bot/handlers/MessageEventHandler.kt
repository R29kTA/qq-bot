package io.github.r29kta.qqbot.bot.handlers

import io.github.r29kta.qqbot.bot.service.MessageService
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MessageEventHandler(private val service:MessageService) {
    @Bean
    fun onGroupMessageReceive() = GlobalEventChannel.subscribeAlways<GroupMessageEvent> {
        service.saveGroupMessage(it.message.source,"")
    }
}