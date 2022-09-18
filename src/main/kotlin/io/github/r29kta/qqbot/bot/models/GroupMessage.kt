package io.github.r29kta.qqbot.bot.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.regex.Pattern


@Document(collection = "groupMessage")
data class GroupMessage(val ids:IntArray,
                        val internalIds:IntArray,
                        val time:Int,
                        val group:Long,
                        val content:String,
                        val images:List<String>,
                        val msgJson:String,
                        val fromId:Long,
                        val recalled:Boolean = false,
                        var publisher:String=""
){
    @Id
    var id="$time$group${internalIds.sum()+ids.sum()}"
    @Field("serial")
    var serial = findSerial(content)


    private fun findSerial(content: String):String{
        val matcher = Pattern.compile("^\\d+").matcher(content)
        return if (matcher.find()) "${matcher.group().toInt()}" else ""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as GroupMessage
        if (!ids.contentEquals(other.ids)) return false
        if (!internalIds.contentEquals(other.internalIds)) return false
        if (time != other.time) return false
        if (group != other.group) return false
        if (content != other.content) return false
        if (images != other.images) return false
        if (msgJson != other.msgJson) return false
        if (recalled != other.recalled) return false
        if (publisher != other.publisher) return false
        if (id != other.id) return false
        if (serial != other.serial) return false

        return true
    }
    override fun hashCode(): Int {
        var result = ids.contentHashCode()
        result = 31 * result + internalIds.contentHashCode()
        result = 31 * result + time
        result = 31 * result + group.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + images.hashCode()
        result = 31 * result + msgJson.hashCode()
        result = 31 * result + recalled.hashCode()
        result = 31 * result + publisher.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + serial.hashCode()
        return result
    }
}