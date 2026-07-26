package com.capybaradev.apocalypse.events

import com.capybaradev.apocalypse.registry.ModAttachments
import com.capybaradev.apocalypse.world.ApocalypseWorldData
import com.capybaradev.apocalypse.world.HighwayGenerator
import com.capybaradev.apocalypse.world.HighwayMapGenerator
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.saveddata.SavedData
import net.neoforged.neoforge.event.level.ChunkEvent
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.level.LevelEvent

@EventBusSubscriber
object WorldEvents {

    @SubscribeEvent
    fun onChunkLoad(event: ChunkEvent.Load) {
        //event.isNewChunk ??? maybe??

        if (event.level !is ServerLevel)
            return

        val chunk = event.chunk
        val level = event.level as ServerLevel
        if (chunk.getData(ModAttachments.HIGHWAY_GENERATED))
            return

        HighwayGenerator.generate(level, chunk)

        chunk.setData(
            ModAttachments.HIGHWAY_GENERATED,
            true
        )

    }
    @SubscribeEvent
    fun onWorldLoad(event: LevelEvent.Load) {

        val level = event.level

        if (level !is ServerLevel)
            return

        val data = level.dataStorage.computeIfAbsent(
            SavedData.Factory(
                ::ApocalypseWorldData,
                ApocalypseWorldData::load
            ),
            "apocalypse_world"
        )

        if (!data.generated) {

            println("Generating highway map")

            HighwayMapGenerator.generate(level)

            data.generated = true
            data.setDirty()
        }
    }
}