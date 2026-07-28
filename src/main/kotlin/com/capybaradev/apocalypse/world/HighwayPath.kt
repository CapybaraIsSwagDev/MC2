package com.capybaradev.apocalypse.world

object HighwayPath {

    fun getHighwayType(
        chunkX: Int,
        chunkZ: Int
    ): RoadType {

        val horizontal = chunkZ % 20 == 0

        val vertical = chunkX % 20 == 0

        if (horizontal && vertical) {
            return RoadType.INTERSECTION
        } else if (horizontal) {
            return RoadType.STRAIGHTX
        } else if (vertical) {
            return RoadType.STRAIGHTZ
        }
        return RoadType.NONE
    }
    fun isHighway(
        chunkX: Int,
        chunkZ: Int
    ): Boolean {
        return getHighwayType(chunkX, chunkZ) == RoadType.NONE
    }


}

enum class RoadType {
    NONE,
    STRAIGHTX,
    STRAIGHTZ,
    INTERSECTION
}
