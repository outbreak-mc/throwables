package space.rimgro.throwables.effects

import org.bukkit.entity.LivingEntity
import space.rimgro.throwables.ThrowablesPlugin

abstract class CustomEntityHitEffect(val plugin: ThrowablesPlugin) {
    abstract val id: String

    abstract fun apply(entity: LivingEntity, args: List<String>?)
}