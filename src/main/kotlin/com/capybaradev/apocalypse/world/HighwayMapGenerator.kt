package com.capybaradev.apocalypse.world

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel


data class HighwayNode(
    val x: Int,
    val z: Int
)

data class HighwayRoad(
    val start: HighwayNode,
    val end: HighwayNode
)

object HighwayMapGenerator {

    var nodes = listOf<HighwayNode>()

    var roads = listOf<HighwayRoad>()

    fun generate(level: ServerLevel) {
        println("GENERATED LEVVEL")
        nodes = listOf(
            HighwayNode(0, 0),
            HighwayNode(500, 200),
            HighwayNode(1000, 100),
            HighwayNode(1500, 600)
        )

        roads = listOf(
            HighwayRoad(nodes[0], nodes[1]),
            HighwayRoad(nodes[1], nodes[2])
        )
    }

    fun isNearRoad(
        chunkX: Int,
        chunkZ: Int
    ): Boolean {

        val blockX = chunkX * 16 + 8
        val blockZ = chunkZ * 16 + 8

        for (road in roads) {
            if (distanceToLine(
                    blockX,
                    blockZ,
                    road.start,
                    road.end
                ) < 16) {
                return true
            }
        }

        return false
    }
    fun closestPointOnRoad(
        blockX: Int,
        blockZ: Int
    ): BlockPos {

        var closest = Double.MAX_VALUE
        var closestRoad: HighwayRoad = HighwayRoad(HighwayNode(0, 0),HighwayNode(0, 0))
        for (road in roads) {
            val distance = distanceToLine(
                blockX,
                blockZ,
                road.start,
                road.end
            )

            if (distance < closest) {
                closest = distance
                closestRoad = road
            }

        }

        val point = closestPointOnLine(
            blockX,
            blockZ,
            closestRoad.start,
            closestRoad.end
        )

        return point
    }
    fun distanceToRoad(
        blockX: Int,
        blockZ: Int
    ): Double {

        var closest = Double.MAX_VALUE

        for (road in roads) {
            val distance = distanceToLine(
                blockX,
                blockZ,
                road.start,
                road.end
            )


            if (distance < closest) {
                closest = distance
            }
        }

        return closest
    }


    private fun distanceToLine(
        x: Int,
        z: Int,
        start: HighwayNode,
        end: HighwayNode
    ) : Double {

        val px = x.toDouble()
        val pz = z.toDouble()

        val ax = start.x.toDouble()
        val az = start.z.toDouble()

        val bx = end.x.toDouble()
        val bz = end.z.toDouble()

        val dx = bx - ax
        val dz = bz - az

        // length squared of the line
        val lengthSquared = dx * dx + dz * dz

        if (lengthSquared == 0.0) {
            // start and end are the same point
            return kotlin.math.sqrt(
                (px - ax) * (px - ax) +
                        (pz - az) * (pz - az)
            )
        }

        // projection factor (0 = start, 1 = end)
        var t = ((px - ax) * dx + (pz - az) * dz) / lengthSquared

        // clamp to the line segment
        t = t.coerceIn(0.0, 1.0)

        // closest point on the line
        val closestX = ax + t * dx
        val closestZ = az + t * dz

        val diffX = px - closestX
        val diffZ = pz - closestZ

        return kotlin.math.sqrt(diffX * diffX + diffZ * diffZ)
    }
    private fun closestPointOnLine (
        x: Int,
        z: Int,
        start: HighwayNode,
        end: HighwayNode
    ): BlockPos{
        val px = x.toDouble()
        val pz = z.toDouble()

        val ax = start.x.toDouble()
        val az = start.z.toDouble()

        val bx = end.x.toDouble()
        val bz = end.z.toDouble()

        val dx = bx - ax
        val dz = bz - az

        // length squared of the line
        val lengthSquared = dx * dx + dz * dz

        if (lengthSquared == 0.0) {
            // start and end are the same point
            return BlockPos(ax.toInt(),0,az.toInt())
        }

        // projection factor (0 = start, 1 = end)
        var t = ((px - ax) * dx + (pz - az) * dz) / lengthSquared

        // clamp to the line segment
        t = t.coerceIn(0.0, 1.0)

        // closest point on the line
        val closestX = ax + t * dx
        val closestZ = az + t * dz

        return BlockPos(closestX.toInt(),0,closestZ.toInt())
    }
}

