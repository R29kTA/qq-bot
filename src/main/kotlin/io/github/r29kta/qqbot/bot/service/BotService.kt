package io.github.r29kta.qqbot.bot.service

import net.mamoe.mirai.event.events.BotOfflineEvent
import net.mamoe.mirai.event.events.BotOnlineEvent

interface BotService {
    fun onBotOnline(botOnlineEvent: BotOnlineEvent)
    fun onBotOffline(botOfflineEvent: BotOfflineEvent)
}