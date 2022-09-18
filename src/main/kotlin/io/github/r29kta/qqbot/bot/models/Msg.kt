package io.github.r29kta.qqbot.bot.models

data class Msg(val target:Long,
               val content:String,
               val images:List<String>,
               val publisher:String
)