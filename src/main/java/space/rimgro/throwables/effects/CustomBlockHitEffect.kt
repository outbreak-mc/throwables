package space.rimgro.throwables.effects

import org.bukkit.Location
import space.rimgro.throwables.ThrowablesPlugin

abstract class CustomBlockHitEffect(val plugin: ThrowablesPlugin) {
    abstract val id: String

    abstract fun apply(hitPoint: Location, args: List<String>?)
}