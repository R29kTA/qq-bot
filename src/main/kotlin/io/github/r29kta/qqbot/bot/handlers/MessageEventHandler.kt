package io.github.r29kta.qqbot.bot.handlers

import io.github.r29kta.qqbot.bot.service.MessageService
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.MessageSerializers
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.data.MessageChain.Companion.serializeToJsonString
import net.mamoe.mirai.message.data.MessageSource.Key.recall
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MessageEventHandler(private val service:MessageService) {
    @Bean
    fun onGroupMessageReceive() = GlobalEventChannel.subscribeAlways<GroupMessageEvent> {
        service.saveGroupMessage(it.message.source,"")
    }
}