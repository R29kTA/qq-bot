package io.github.r29kta.qqbot.bot.service.impl

import io.github.r29kta.qqbot.bot.models.Bot
import io.github.r29kta.qqbot.bot.models.Group
import io.github.r29kta.qqbot.bot.repository.BotRepository
import io.github.r29kta.qqbot.bot.service.BotService
import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.event.events.BotOfflineEvent
import net.mamoe.mirai.event.events.BotOnlineEvent
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BotServiceImpl(private val repository: BotRepository):BotService {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    override fun onBotOnline(botOnlineEvent: BotOnlineEvent) {
        val groups = mutableListOf<Group>()
        botOnlineEvent.bot.groups.forEach{ groups.add(Group(it.id,it.botPermission.isOperator())) }
        val bot = repository.save(Bot(botOnlineEvent.bot.id.toString(), groups, ""))
        bot.subscribe {
            logger.info("Bot:${it.id}同步了${it.groups.size}个群，地址：")
        }
    }
    override fun onBotOffline(botOfflineEvent: BotOfflineEvent) {
        val id = botOfflineEvent.bot.id.toString()
        repository.deleteById(id).subscribe()
        logger.info("bot已离线")
    }
}