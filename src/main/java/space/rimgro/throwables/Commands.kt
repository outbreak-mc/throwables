package space.rimgro.throwables

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.Argument
import dev.jorel.commandapi.arguments.IntegerArgument
import dev.jorel.commandapi.arguments.PlayerArgument
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

class Commands(private val plugin: ThrowablesPlugin) {

    init {
        CommandAPICommand("throwables")
            .withSubcommand(
                CommandAPICommand("set")
                    .withArguments(StringArgument("id"))
                    .executesPlayer(PlayerCommandExecutor{ player, args ->
                        val item = player.inventory.itemInMainHand
                        val itemMeta = item.itemMeta

                        itemMeta.persistentDataContainer.set(
                            NamespacedKey(plugin, "throwable_id"),
                            PersistentDataType.STRING,
                            args[0].toString()
                        )

                        item.itemMeta = itemMeta
                    })
            )
            .withSubcommand(
                CommandAPICommand("remove")
                    .executesPlayer(PlayerCommandExecutor{ player, _ ->
                        val item = player.inventory.itemInMainHand
                        val itemMeta = item.itemMeta

                        itemMeta.persistentDataContainer.remove(
                            NamespacedKey(plugin, "throwable_id")
                        )

                        item.itemMeta = itemMeta
                    })
            )
            .withSubcommand(
                CommandAPICommand("reload")
                    .executesPlayer(PlayerCommandExecutor{ player, _ ->
                        plugin.throwableManager.reloadFromConfig()
                    })
            )
            .register()
    }
}