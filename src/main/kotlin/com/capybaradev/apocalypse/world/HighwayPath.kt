package com.capybaradev.apocalypse.world

object HighwayPath {

    fun getHighwayType(
        chunkX: Int,
        chunkZ: Int
    ): HighwayType.RoadType {

        val horizontal = chunkZ % 20 == 0

        val vertical = chunkX % 20 == 0

        if (horizontal && vertical) {
            return HighwayType.RoadType.INTERSECTION
        } else if (horizontal) {
            return HighwayType.RoadType.STRAIGHTX
        } else if (vertical) {
            return HighwayType.RoadType.STRAIGHTZ
        }
        return HighwayType.RoadType.NONE
    }
    fun isHighway(
        chunkX: Int,
        chunkZ: Int
    ): Boolean {
        return getHighwayType(chunkX, chunkZ) == HighwayType.RoadType.NONE
    }


}

class HighwayType {
    enum class RoadType {
        NONE, 
        STRAIGHTX,
        STRAIGHTZ,
        INTERSECTION
    }
}