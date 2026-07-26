package com.capybaradev.apocalypse.world

import net.minecraft.world.level.levelgen.LegacyRandomSource
import net.minecraft.world.level.levelgen.WorldgenRandom
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise

class HighwayNoise(seed: Long) {

    private val noise = PerlinSimplexNoise(
        WorldgenRandom(LegacyRandomSource(seed)),
        listOf(0)
    )
    fun getHeight(x: Int, z: Int): Int {

        val noiseValue = noise.getValue(
            x / 200.0,
            z / 200.0,
            false
        )

        return (83 + noiseValue * 20).toInt()
    }
}