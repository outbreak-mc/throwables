package space.rimgro.throwables

import ThrowablesListener
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import dev.jorel.commandapi.CommandAPICommand
import org.bukkit.plugin.java.JavaPlugin
import space.rimgro.throwables.effects.CustomEffectManager

class ThrowablesPlugin : JavaPlugin() {
    lateinit var commands: Commands
    lateinit var effectManager: CustomEffectManager
    lateinit var throwableManager: ThrowableManager


    override fun onLoad() {
        CommandAPI.onLoad(CommandAPIBukkitConfig(this).silentLogs(true))
    }

    override fun onEnable() {
        saveDefaultConfig()
        effectManager = CustomEffectManager(this)
        throwableManager = ThrowableManager(this)
        commands = Commands(this)
        server.pluginManager.registerEvents(ThrowablesListener(this), this)
    }

    override fun onDisable() {
    }
}