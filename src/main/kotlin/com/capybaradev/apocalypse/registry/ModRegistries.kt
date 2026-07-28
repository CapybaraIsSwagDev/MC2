package com.capybaradev.apocalypse.registry

import com.capybaradev.apocalypse.Apocalypse
import com.capybaradev.apocalypse.data.HighwaySegment
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation

object ModRegistries {

    val HIGHWAY_SEGMENT = ResourceKey.createRegistryKey<HighwaySegment>(
        ResourceLocation.fromNamespaceAndPath(
            Apocalypse.MOD_ID,
            "highway_segment"
        )
    )
}