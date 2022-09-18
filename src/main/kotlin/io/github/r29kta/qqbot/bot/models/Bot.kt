package io.github.r29kta.qqbot.bot.models

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "bot")
data class Bot(val id:String,val groups:List<Group>,val url:String)
data class Group(val group:Long,val isOperator:Boolean)