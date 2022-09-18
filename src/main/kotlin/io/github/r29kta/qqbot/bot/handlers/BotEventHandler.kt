package io.github.r29kta.qqbot.bot.handlers

import io.github.r29kta.qqbot.bot.service.BotService
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotOfflineEvent
import net.mamoe.mirai.event.events.BotOnlineEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BotEventHandler(private val botService: BotService) {
    @Bean
    fun onBotOnline() = GlobalEventChannel.subscribeAlways<BotOnlineEvent>{
        botService.onBotOnline(it)
    }
    @Bean
    fun onBotOffline() = GlobalEventChannel.subscribeAlways<BotOfflineEvent> {
        botService.onBotOffline(it)
    }
//    @Bean
//    fun onGroup() = GlobalEventChannel.subscribeAlways<Message> {  }
}
