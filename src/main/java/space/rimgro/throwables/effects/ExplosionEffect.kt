package space.rimgro.throwables.effects

import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import space.rimgro.throwables.ThrowablesPlugin

class EntityExplosionEffect(plugin: ThrowablesPlugin) : CustomEntityHitEffect(plugin) {
    override val id: String = "EXPLOSION"

    override fun apply(entity: LivingEntity, args: List<String>?) {
        if (args?.size == 0) return
        entity.location.world.createExplosion(entity.location, args!![0].toFloatOrNull()!!)
    }
}

class BlockExplosionEffect(plugin: ThrowablesPlugin) : CustomBlockHitEffect(plugin) {
    override val id: String = "EXPLOSION"

    override fun apply(hitPoint: Location, args: List<String>?) {
        if (args?.size == 0) return
        hitPoint.world.createExplosion(hitPoint, args!![0].toFloatOrNull()!!)
    }
}