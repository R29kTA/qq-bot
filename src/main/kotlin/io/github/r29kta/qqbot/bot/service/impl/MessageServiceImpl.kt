package io.github.r29kta.qqbot.bot.service.impl

import io.github.r29kta.qqbot.bot.models.GroupMessage
import io.github.r29kta.qqbot.bot.models.Msg
import io.github.r29kta.qqbot.bot.repository.GroupMessageRepository
import io.github.r29kta.qqbot.bot.service.MessageService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.withContext
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.message.MessageReceipt
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.data.Image.Key.queryUrl
import net.mamoe.mirai.message.data.MessageChain.Companion.serializeToJsonString
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import okio.use
import org.springframework.stereotype.Service
import java.net.URL
import java.util.*

@Service
class MessageServiceImpl(private val repository: GroupMessageRepository) : MessageService {
    override suspend fun saveGroupMessage(source: MessageSource,publisher:String) {
        val messageChain = buildMessageChain {
            this.add(source)
        }
        val mc = source.originalMessage
        val images = mc.filterIsInstance<Image>()
        val msg = mc.filterIsInstance<PlainText>()
        val content = buildString { msg.forEach(::append) }
        val imageUrls = mutableListOf<String>()
        images.forEach {
            imageUrls.add(it.queryUrl())
        }
        val msgS = GroupMessage(
            ids = source.ids,
            internalIds = source.internalIds,
            time = source.time,
            group = source.targetId,
            content = content,
            images = imageUrls,
            msgJson = messageChain.serializeToJsonString(),
            fromId = source.fromId
        )
        if (publisher.isNotBlank()){
            msgS.publisher = publisher
        }
        repository.save(msgS).subscribe {
            println(it)
        }
    }

    override suspend fun sendGroupMessage(msg: Msg): MessageReceipt<Group> {
        val bot = Bot.instances[0]
        val id = msg.target
        val group = bot.groups[id]
        val m = buildMessageChain {
            this.add(PlainText("${getCurrentSerial(id)}.${msg.content}"))
            msg.images.forEach {
                this.add(getImageByUrl(it, group!!))
            }
        }
        return group!!.sendMessage(m)
    }

    override suspend fun recallMessage() {
        TODO("Not yet implemented")
    }

    suspend fun getImageByUrl(url: String, contact: Contact): Image {
        val u = URL(url)
        return withContext(Dispatchers.IO) {
            u.openConnection().getInputStream().use {
                it.toExternalResource().use { res ->
                    res.uploadAsImage(contact)
                }
            }
        }
    }

    suspend fun getCurrentSerial(id: Long): Int {
        val msg = repository.findFirstByGroupAndSerialIsNotOrderByTimeDesc(id).awaitFirstOrNull()
        msg ?: return 1
        return if (differentDays(msg.time.toLong() * 1000)) {
            msg.serial.toInt() + 1
        } else {
            1
        }
    }

    private fun differentDays(t1: Long): Boolean {
        val cal1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"))
        cal1.time = Date(t1)
        val cal2 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"))
        cal2.time = Date()
        val day1 = cal1[Calendar.DAY_OF_YEAR]
        val day2 = cal2[Calendar.DAY_OF_YEAR]
        val year1 = cal1[Calendar.YEAR]
        val year2 = cal2[Calendar.YEAR]
        val diff = if (year1 != year2) //同一年
        {
            var timeDistance = 0
            for (i in year1 until year2) {
                timeDistance += if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) //闰年
                {
                    366
                } else  //不是闰年
                {
                    365
                }
            }
            timeDistance + (day2 - day1)
        } else  //不同年
        {
            day2 - day1
        }
        return diff == 0
    }
}
