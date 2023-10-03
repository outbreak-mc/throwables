package space.rimgro.throwables.effects

import org.bukkit.entity.LivingEntity
import space.rimgro.throwables.ThrowablesPlugin

class DamageEffect(plugin: ThrowablesPlugin) : CustomEntityHitEffect(plugin) {
    override val id: String = "DAMAGE"

    override fun apply(entity: LivingEntity, args: List<String>?) {
        if (args?.size == 0) return
        entity.damage(args!![0].toDoubleOrNull()!!);
    }
}