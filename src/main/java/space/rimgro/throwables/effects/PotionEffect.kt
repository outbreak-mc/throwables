package space.rimgro.throwables.effects

import org.bukkit.entity.LivingEntity
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import space.rimgro.throwables.ThrowablesPlugin

class PotionEffect(plugin: ThrowablesPlugin) : CustomEntityHitEffect(plugin) {
    override val id: String = "POTION_EFFECT"

    override fun apply(entity: LivingEntity, args: List<String>?) {
        if (args == null) return
        if (args.size < 2) return

        val effectType = PotionEffectType.getByName(args[0].uppercase())
        if (effectType == null) {
            plugin.logger.severe("Error while parsing POTION_EFFECT from config, can't find potion effect with name: " + args[0])
            return
        }

        var duration = 1;
        try {
            duration = Integer.parseInt(args[1])
        } catch (e: NumberFormatException){
            plugin.logger.severe("Error while parsing POTION_EFFECT from config, duration is not a number")
            return
        }

        if (args.size < 3)
        {
            entity.addPotionEffect(PotionEffect(effectType, duration, 1, false, false))
            return
        }


        var amplifier = 1
        try {
            amplifier = Integer.parseInt(args[2])
        } catch (e: NumberFormatException){
            plugin.logger.severe("Error while parsing POTION_EFFECT from config, level is not a number")
            return
        }

        entity.addPotionEffect(PotionEffect(effectType, duration, amplifier, false, false))
    }
}