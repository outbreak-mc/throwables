package space.rimgro.throwables

import org.bukkit.configuration.file.YamlConfiguration
import space.rimgro.throwables.effects.CustomBlockHitEffectInstanceData
import space.rimgro.throwables.effects.CustomEntityHitEffectInstanceData
import java.io.File


import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue

data class Config(
    val throwables: Map<String, ThrowableData>
)

data class ThrowableData(
    @JsonProperty("entity-hit")
    val entityHit: List<String> = listOf(),

    @JsonProperty("block-hit")
    val blockHit: List<String> = listOf()
){
    val entityHitParsed: List<CustomEntityHitEffectInstanceData> by lazy {
        val out = mutableListOf<CustomEntityHitEffectInstanceData>()
        for (e in entityHit) {
            val parts = e.split(":")
            val effect = parts[0]
            if (effect != null) {
                val args = parts.drop(1)
                out.add(CustomEntityHitEffectInstanceData(effect, args))
            }
        }
        out
    }

    val blockHitParsed: List<CustomBlockHitEffectInstanceData> by lazy {
        val out = mutableListOf<CustomBlockHitEffectInstanceData>()
        for (e in blockHit) {
            val parts = e.split(":")
            val effect = parts[0]
            if (effect != null) {
                val args = parts.drop(1)
                out.add(CustomBlockHitEffectInstanceData(effect, args))
            }
        }
        out
    }
}

class ThrowableManager(val plugin: ThrowablesPlugin) {
    val throwables = mutableMapOf<String, Throwable>()

    init {
        reloadFromConfig()
    }

    public fun reloadFromConfig(){
        val mapper: ObjectMapper = YAMLMapper()
            .registerModule(
                KotlinModule.Builder()
                    .withReflectionCacheSize(512)
                    .configure(KotlinFeature.NullToEmptyCollection, false)
                    .configure(KotlinFeature.NullToEmptyMap, false)
                    .configure(KotlinFeature.NullIsSameAsDefault, false)
                    .configure(KotlinFeature.SingletonSupport, false)
                    .configure(KotlinFeature.StrictNullChecks, false)
                    .build()
            )
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        val conf = mapper.readValue(plugin.config.saveToString(), Config::class.java).throwables.forEach {
            throwables[it.key] = Throwable(it.key, it.value.entityHitParsed, it.value.blockHitParsed)
        }
    }

}