import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Snowball
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import space.rimgro.throwables.ThrowablesPlugin
import space.rimgro.throwables.effects.CustomEntityHitEffect

class ThrowablesListener(private val plugin: ThrowablesPlugin) : Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val item = event.item

        if (item != null && isThrowable(item)) {
            val projectile = event.player.launchProjectile(Snowball::class.java, event.player.eyeLocation.direction)
            projectile.item = item.clone()
            event.isCancelled = true

            if (item.amount > 1) {
                item.amount = item.amount - 1
            } else {
                event.player.inventory.setItemInMainHand(null)
            }
        }
    }

    @EventHandler
    fun onSnowballHit(event: ProjectileHitEvent) {
        val projectile = event.entity
        if (projectile is Snowball) {
            val item = projectile.item

            if (isThrowable(item)) {
                val itemMeta = item.itemMeta
                val throwable_id = itemMeta.persistentDataContainer.get(
                    NamespacedKey(plugin, "throwable_id"),
                    PersistentDataType.STRING)

                if (throwable_id !in plugin.throwableManager.throwables) return

                val throwableClass = plugin.throwableManager.throwables[throwable_id]
                val hitEntity = event.hitEntity
                if (hitEntity is LivingEntity) {
                    throwableClass?.entityHitEffects?.forEach{
                        val e = plugin.effectManager.getEntityHitEffectFromId(it.effect)
                        e?.apply(hitEntity, it.args)
                    }
                    return
                }

                val hitBlock = event.hitBlock
                if (hitBlock is Block){
                    throwableClass?.blockHitEffects?.forEach{
                        val e = plugin.effectManager.getBlockHitEffectFromId(it.effect)
                        e?.apply(projectile.location ,it.args)
                    }
                    return
                }
            }
        }
    }


    fun isThrowable(item: ItemStack): Boolean {
        val itemMeta = item.itemMeta
        return itemMeta.persistentDataContainer.has(
            NamespacedKey(plugin, "throwable_id"),
            PersistentDataType.STRING
        )
    }
}