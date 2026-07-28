package com.capybaradev.apocalypse

import com.capybaradev.apocalypse.data.HighwaySegment
import com.capybaradev.apocalypse.registry.ModAttachments
import com.capybaradev.apocalypse.registry.ModBlocks
import com.capybaradev.apocalypse.registry.ModItems
import com.capybaradev.apocalypse.registry.ModRegistries
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.registries.DataPackRegistryEvent

@Mod(Apocalypse.MOD_ID)
class Apocalypse(
    modEventBus: IEventBus
)  {

    companion object {
        const val MOD_ID = "apocalypse"
    }

    init {
        ModAttachments.ATTACHMENTS.register(modEventBus)
        ModBlocks.BLOCKS.register(modEventBus)
        ModItems.ITEMS.register(modEventBus)
        println("My Kotlin NeoForge mod loaded!")
        modEventBus.addListener(::registerDataPackRegistries)

    }
    private fun registerDataPackRegistries(event: DataPackRegistryEvent.NewRegistry) {
        event.dataPackRegistry(
            ModRegistries.HIGHWAY_SEGMENT,
            HighwaySegment.CODEC,
            HighwaySegment.CODEC
        )
    }
}