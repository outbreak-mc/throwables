package space.rimgro.throwables

import space.rimgro.throwables.effects.CustomBlockHitEffectInstanceData
import space.rimgro.throwables.effects.CustomEntityHitEffect
import space.rimgro.throwables.effects.CustomEntityHitEffectInstanceData

class Throwable(val id: String,
                val entityHitEffects: List<CustomEntityHitEffectInstanceData>,
                val blockHitEffects: List<CustomBlockHitEffectInstanceData>) {

}