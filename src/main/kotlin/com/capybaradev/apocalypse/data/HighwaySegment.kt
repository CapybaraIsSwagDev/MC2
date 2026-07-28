package com.capybaradev.apocalypse.data

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder

data class HighwaySegment(
    val width: Int,
    val height: Int,
    val material: String
) {
    companion object {
        val CODEC: Codec<HighwaySegment> = RecordCodecBuilder.create { instance ->
            instance.group(
                Codec.INT.fieldOf("width").forGetter(HighwaySegment::width),
                Codec.INT.fieldOf("height").forGetter(HighwaySegment::height),
                Codec.STRING.fieldOf("material").forGetter(HighwaySegment::material)
            ).apply(instance, ::HighwaySegment)
        }
    }
}