package space.rimgro.throwables.effects;

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import space.rimgro.throwables.ThrowablesPlugin
import java.util.ArrayList;

class CustomEffectManager {
    private val registeredEntityHitEffects: MutableMap<String, CustomEntityHitEffect> = mutableMapOf()
    private val registeredBlockHitEffects: MutableMap<String, CustomBlockHitEffect> = mutableMapOf()
    val plugin: ThrowablesPlugin

    constructor(plugin: ThrowablesPlugin){
        this.plugin = plugin
        // Тут мы регистиуем эффекты
        registerEntityHitEffect(PotionEffect(plugin))
        registerEntityHitEffect(FreezeEffect(plugin))
        registerEntityHitEffect(DamageEffect(plugin))
        registerEntityHitEffect(EntityExplosionEffect(plugin))
        registerBlockHitEffect(BlockExplosionEffect(plugin))
    }

    fun registerEntityHitEffect(effect: CustomEntityHitEffect){
        registeredEntityHitEffects[effect.id.uppercase()] = effect
    }

    fun registerBlockHitEffect(effect: CustomBlockHitEffect){
        registeredBlockHitEffects[effect.id.uppercase()] = effect
    }

    fun getEntityHitEffectFromId(id: String) : CustomEntityHitEffect? {
        if (registeredEntityHitEffects.containsKey(id)){
            return registeredEntityHitEffects[id.uppercase()]
        }
        return null
    }

    fun getBlockHitEffectFromId(id: String) : CustomBlockHitEffect? {
        if (registeredBlockHitEffects.containsKey(id)){
            return registeredBlockHitEffects[id.uppercase()]
        }
        return null
    }
}
