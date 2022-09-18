package io.github.r29kta.qqbot.bot


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.utils.BotConfiguration
import net.mamoe.mirai.utils.LoggerAdapters.asMiraiLogger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import kotlin.system.exitProcess


@Configuration
class Login : CommandLineRunner{
    private val logger = LoggerFactory.getLogger(this.javaClass)
    override fun run(vararg args: String) {
        if (args.size<2){
            logger.error("bot帐号密码参数不够")
            exitProcess(1)
        }
        try {
            val account = args[0].toLong()
            val password = args[1]
            logger.info("尝试使用帐号:${account},密码:${password}登录")
            CoroutineScope(Dispatchers.IO).launch{
                BotFactory.newBot(account,password, defaultBotConfiguration()).login()
            }
        }catch (_:NumberFormatException){
            logger.error("帐号输入不合法")
            exitProcess(1)
        }
    }
}
fun defaultBotConfiguration(): BotConfiguration {
    return object : BotConfiguration() {
        init {
            fileBasedDeviceInfo()
            protocol = MiraiProtocol.ANDROID_PHONE
            networkLoggerSupplier = {LoggerFactory.getLogger("Net ${it.id}").asMiraiLogger()}
            botLoggerSupplier = {LoggerFactory.getLogger("Bot ${it.id}").asMiraiLogger()}
        }
    }
}