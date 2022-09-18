package io.github.r29kta.qqbot.bot.service

import io.github.r29kta.qqbot.bot.models.Msg
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.message.MessageReceipt
import net.mamoe.mirai.message.data.MessageSource

interface MessageService {
    suspend fun saveGroupMessage(source: MessageSource, publisher: String)
    suspend fun sendGroupMessage(msg: Msg): MessageReceipt<Group>
    suspend fun recallMessage()

}