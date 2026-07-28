package com.capybaradev.apocalypse.data

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider

data class HighwaySegment(
    val width: Int,
    val height: Int,
    val material: BlockStateProvider
) {
    companion object {
        val CODEC = RecordCodecBuilder.create { instance ->
            instance.group(
                Codec.INT.fieldOf("width").forGetter(HighwaySegment::width),
                Codec.INT.fieldOf("height").forGetter(HighwaySegment::height),
                BlockStateProvider.CODEC.fieldOf("material").forGetter(HighwaySegment::material)
            ).apply(instance, ::HighwaySegment)
        }
    }
}