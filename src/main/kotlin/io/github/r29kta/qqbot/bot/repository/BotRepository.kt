package io.github.r29kta.qqbot.bot.repository

import io.github.r29kta.qqbot.bot.models.Bot
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BotRepository : ReactiveMongoRepository<Bot,String>