package com.capybaradev.apocalypse

import com.capybaradev.apocalypse.registry.ModAttachments
import com.capybaradev.apocalypse.registry.ModBlocks
import com.capybaradev.apocalypse.registry.ModItems
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod

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
    }
}