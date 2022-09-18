package io.github.r29kta.qqbot.bot.repository

import io.github.r29kta.qqbot.bot.models.GroupMessage
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface GroupMessageRepository : ReactiveMongoRepository<GroupMessage,String>{
    fun findFirstByGroupAndSerialIsNotOrderByTimeDesc(group: Long, serial: String = ""):Mono<GroupMessage>
}