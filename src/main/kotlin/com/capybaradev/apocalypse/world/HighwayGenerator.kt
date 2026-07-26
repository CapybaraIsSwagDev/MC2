package com.capybaradev.apocalypse.world

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.chunk.ChunkAccess

object HighwayGenerator {

    fun generate(
        level: ServerLevel,
        chunk: ChunkAccess
    ) {
        //println("Generator called: ${chunk.pos}")

        val chunkX = chunk.pos.x
        val chunkZ = chunk.pos.z
        if (HighwayPath.isHighway(chunkX,chunkZ) == HighwayType.RoadType.NONE) {
            return
        }

        //println("Generating highway")

        generateRoad(level, chunk)
    }


    private fun generateRoad(
        level: ServerLevel,
        chunk: ChunkAccess
    ) {
        HighwaySegment.create(level,chunk,0)
    }
}


