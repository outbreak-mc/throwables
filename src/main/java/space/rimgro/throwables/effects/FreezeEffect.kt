package space.rimgro.throwables.effects

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import space.rimgro.throwables.ThrowablesPlugin

class FreezeEffect(plugin: ThrowablesPlugin) : CustomEntityHitEffect(plugin) {
    override val id: String = "FREEZE"

    override fun apply(entity: LivingEntity, args: List<String>?) {
        entity.freezeTicks = Integer.parseInt(args?.get(0) ?: "0");
    }
}