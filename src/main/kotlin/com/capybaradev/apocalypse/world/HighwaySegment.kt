package com.capybaradev.apocalypse.world


import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.TagKey
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.chunk.ChunkAccess
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider

object HighwaySegment {
    val baseY = 80
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

        //val highwayNoise = HighwayNoise(level.seed)
        //highwayNoise.getHeight(worldX, worldZ)
        //val averageY = getAverageHeight(level, chunk.pos)

        //val road = HighwayPath.getHighwayType(worldX,worldZ)

        when (type) {
            RoadType.STRAIGHTX -> {
                //println("| road")
                val changedPositions = mutableListOf<BlockPos>()
                for (y in -1 until 8) {
                    for (x in 0 until 16) {
                        for (z in 0 until 16) {
                            var block = Blocks.AIR.defaultBlockState()

                            if (y == 0) {
                                block = Blocks.BLACK_CONCRETE.defaultBlockState()
                                if (z !in 2..14) {
                                    block = Blocks.SMOOTH_STONE.defaultBlockState()
                                }
                            } else if (y == -1) {
                                block = Blocks.STONE_BRICKS.defaultBlockState()
                            }
                            changedPositions.add(BlockPos(worldX * 16 + x, baseY + y, worldX * 16 + z))
                            chunk.setBlockState(BlockPos(x, baseY + y, z), block, false)
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
                val changedPositions = mutableListOf<BlockPos>()
                for (y in -1 until 8) {
                    for (x in 0 until 16) {
                        for (z in 0 until 16) {
                            var block = Blocks.AIR.defaultBlockState()

                            if (y == 0) {
                                block = Blocks.BLACK_CONCRETE.defaultBlockState()
                                if (x !in 2..14) {
                                    block = Blocks.SMOOTH_STONE.defaultBlockState()
                                }
                            } else if (y == -1) {
                                block = Blocks.STONE_BRICKS.defaultBlockState()
                            }
                            changedPositions.add(BlockPos(worldX * 16 + x, baseY + y, worldX * 16 + z))
                            chunk.setBlockState(BlockPos(x, baseY + y, z), block, false)
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
                val changedPositions = mutableListOf<BlockPos>()
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
                            changedPositions.add(BlockPos(worldX * 16 + x, baseY + y, worldX * 16 + z))
                            chunk.setBlockState(BlockPos(x, baseY + y, z), block, false)
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
//            if (abs(baseY - averageY) > 5) {
//                val pos = BlockPos(8, baseY, 8)
//                println(pos)
//                createPier(chunk,pos.x,pos.z,baseY)
//                //createPillar(chunk, baseY)
//            }


        level.chunkSource.lightEngine.lightChunk(chunk, true)
        val pos = BlockPos(8, baseY, 8)
        createPier(chunk, pos.x, pos.z,type)

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
    fun createArch(
        chunk: ChunkAccess,
        start: BlockPos,
        end: BlockPos,
        archHeight: Int
    ) {

        val dx = end.x - start.x
        val dz = end.z - start.z

        val length = maxOf(kotlin.math.abs(dx), kotlin.math.abs(dz))

        for (i in 0..length) {

            val t = i.toDouble() / length

            val x = start.x + dx * t
            val z = start.z + dz * t

            // Parabolic arch
            val y = start.y -
                    (4.0 * archHeight * t * (1.0 - t))

            chunk.setBlockState(
                BlockPos(
                    x.toInt(),
                    y.toInt(),
                    z.toInt()
                ),
                Blocks.STONE_BRICKS.defaultBlockState(),
                false
            )
        }
    }

    private fun createPier(
        chunk: ChunkAccess,
        centerX: Int,
        centerZ: Int,
        type: RoadType
    ) {
        val bottomY = baseY - 2
        for (y in bottomY downTo chunk.minBuildHeight) {

            val pos = BlockPos(centerX, y, centerZ)

            println("try building Pear")
            println(chunk.getBlockState(pos))
            println("building Pear")
            var block = Blocks.STONE_BRICKS.defaultBlockState()

            var dim = Dimension(1, 1, 1, 1)
            if (y > bottomY - 2) {
                block = Blocks.GOLD_BLOCK.defaultBlockState()
                dim = Dimension(7, 8, 6, 7)
            }

            // pier
            if (type == RoadType.STRAIGHTX) {
                dim.rotate()
            }
            for (dx in -dim.minX..dim.maxX) {
                for (dz in -dim.minZ..dim.maxZ) {
                    //if (chunk.getBlockState(pos).`is`(HIGHWAY_REPLACEABLE))
                    chunk.setBlockState(
                        BlockPos(centerX + dx, y, centerZ + dz),
                        block,
                        false
                    )
                }
            }
            if (type == RoadType.INTERSECTION) {
                dim.rotate()
                for (dx in -dim.minX..dim.maxX) {
                    for (dz in -dim.minZ..dim.maxZ) {
                        //if (chunk.getBlockState(pos).`is`(HIGHWAY_REPLACEABLE))
                        chunk.setBlockState(
                            BlockPos(centerX + dx, y, centerZ + dz),
                            block,
                            false
                        )
                    }
                }
            }
        }
    }
}
class Dimension(var minX: Int, var minZ: Int, var maxX: Int, var maxZ: Int) {
    /**
    * Rotates dimensions
     *
     *
    * */
    fun rotate() {
        val oldminZ = this.minZ
        val oldmaxZ = this.maxZ
        val oldminX = this.minX
        val oldmaxX = this.maxX
        this.minX = oldminZ
        this.minZ = oldminX
        this.maxX = oldmaxZ
        this.maxZ = oldmaxX
    }



}