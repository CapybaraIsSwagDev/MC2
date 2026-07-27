package com.capybaradev.apocalypse.world


import com.capybaradev.apocalypse.world.HighwayType.RoadType
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.chunk.ChunkAccess
import net.minecraft.world.level.levelgen.Heightmap
import java.awt.ComponentOrientation

import kotlin.math.abs

object HighwaySegment {
    fun getAverageHeight(
        level: ServerLevel,
        pos: ChunkPos
    ): Int {
        val points = arrayOf(
            0 to 0,
            15 to 0,
            0 to 15,
            15 to 15,
            8 to 8
        )

        var height = 0

        for ((x, z) in points) {
            height += level.getHeight(
                Heightmap.Types.WORLD_SURFACE,
                pos.minBlockX + x,
                pos.minBlockZ + z
            )
        }

        val average = height / points.size
        return average
    }

    fun create(
        level: ServerLevel,
        chunk: ChunkAccess,
        type: RoadType
        ) {
            val worldX = chunk.pos.x
            val worldZ = chunk.pos.z

            val highwayNoise = HighwayNoise(level.seed)
            //highwayNoise.getHeight(worldX, worldZ)
            val averageY = getAverageHeight(level, chunk.pos)
            val baseY = 80

            //val road = HighwayPath.getHighwayType(worldX,worldZ)

            when (type) {
                RoadType.STRAIGHTX -> {
                    //println("| road")
                    var changedPositions = mutableListOf<BlockPos>()
                    for (y in -1 until 8) {
                        for (x in 0 until 16) {
                            for (z in 0 until 16) {
                                var block = Blocks.AIR.defaultBlockState()

                                if (y == 0) {
                                    block = Blocks.BLACK_CONCRETE.defaultBlockState()
                                    if (z <= 1 || z >= 14) {
                                        block = Blocks.SMOOTH_STONE.defaultBlockState()
                                    }
                                } else if (y == -1) {
                                    block = Blocks.STONE_BRICKS.defaultBlockState()
                                }
                                changedPositions.add(BlockPos(worldX * 16 + x, baseY+y, worldX * 16 + z))
                                chunk.setBlockState(BlockPos(x, baseY+y, z),block,false)
                            }
                        }
                    }
                    val lightEngine = level.chunkSource.lightEngine

                    for (pos in changedPositions) {
                        lightEngine.checkBlock(pos)
                    }
                }
                RoadType.STRAIGHTZ -> {
                    //println("- road")
                    var changedPositions = mutableListOf<BlockPos>()
                    for (y in -1 until 8) {
                        for (x in 0 until 16) {
                            for (z in 0 until 16) {
                                var block = Blocks.AIR.defaultBlockState()

                                if (y == 0) {
                                    block = Blocks.BLACK_CONCRETE.defaultBlockState()
                                    if (x <= 1 || x >= 14) {
                                        block = Blocks.SMOOTH_STONE.defaultBlockState()
                                    }
                                } else if (y == -1) {
                                    block = Blocks.STONE_BRICKS.defaultBlockState()
                                }
                                changedPositions.add(BlockPos(worldX * 16 + x, baseY+y, worldX * 16 + z))
                                chunk.setBlockState(BlockPos(x, baseY+y, z),block,false)
                            }
                        }
                    }
                    val lightEngine = level.chunkSource.lightEngine

                    for (pos in changedPositions) {
                        lightEngine.checkBlock(pos)
                    }
                }
                RoadType.INTERSECTION -> {
                    println("X road")
                    var changedPositions = mutableListOf<BlockPos>()
                    for (y in -1 until 8) {
                        for (x in 0 until 16) {
                            for (z in 0 until 16) {
                                var block = Blocks.AIR.defaultBlockState()
                                if (y == 0) {
                                    block = Blocks.BLACK_CONCRETE.defaultBlockState()
                                    if ((x <= 1 || x >= 14) && (z <= 1 || z >= 14)) {
                                        block = Blocks.SMOOTH_STONE.defaultBlockState()
                                    }
                                } else if (y == -1) {
                                    block = Blocks.STONE_BRICKS.defaultBlockState()
                                }                                
                                changedPositions.add(BlockPos(worldX * 16 + x, baseY+y, worldX * 16 + z))
                                chunk.setBlockState(BlockPos(x, baseY+y, z),block,false)
                            }
                        }
                    }
                    val lightEngine = level.chunkSource.lightEngine

                    for (pos in changedPositions) {
                        lightEngine.checkBlock(pos)
                    }
                }
                RoadType.NONE -> {
                    println("O road $worldX $worldZ")
                    return
                }
            }
            if (abs(baseY - averageY) > 5) {
                val pos = BlockPos(8, baseY, 8)
                println(pos)
                createPillar(chunk, baseY)
            }

            level.chunkSource.lightEngine.lightChunk(chunk,true)

            // RANDOM POINTS //
    //        var blockplaced = 0
    //        for (y in -1 until 16) {
    //            for (x in 0 until 16) {
    //                for (z in 0 until 16) {
    //                    val blockX = worldX * 16 + x
    //                    val blockZ = worldZ * 16 + z
    //                    if (HighwayMapGenerator.distanceToRoad(blockX, blockZ) <= 5.0) {
    //                        var block = Blocks.AIR.defaultBlockState()
    //                        if (y == 0) {
    //                            block = Blocks.BLACK_CONCRETE.defaultBlockState()
    //                            blockplaced +=1
    //                        } else if (y == -1) {
    //                            block = Blocks.STONE_BRICKS.defaultBlockState()
    //                        }
    //                        chunk.setBlockState(
    //                            BlockPos(x, baseY+y, z),
    //                            block,
    //                            false
    //                        )
    //
    //                    }
    //                }
    //            }
    //        }
    //        if (blockplaced > 128) {
    //            val blockX = worldX * 16 + 8
    //            val blockZ = worldZ * 16 + 8
    //            val pos = HighwayMapGenerator.closestPointOnRoad(blockX, blockZ)
    //            createPillar(chunk, pos.x, baseY, pos.z)
    //        }


    //



        }
    private val HIGHWAY_REPLACEABLE = TagKey.create(
        Registries.BLOCK,
        ResourceLocation.fromNamespaceAndPath(
            "apocalypse",
            "replaceable"
        )
    )
    private fun createPillar(chunk: ChunkAccess, Y: Int) {
        for (x in 0 until 16) {
            for (z in 0 until 16) {
                for (y in 2 until 32) {
                    if (x == y) {
                        break
                    }
                    val pos = BlockPos(x, Y - y, z)
                    val state = chunk.getBlockState(pos)
                    if (state.`is`(HIGHWAY_REPLACEABLE)) {
                        break
                    }
                    val block = Blocks.STONE_BRICKS.defaultBlockState()
                    chunk.setBlockState(
                        pos,
                        block,
                        false
                    )
                }
            }
        }
    }


}