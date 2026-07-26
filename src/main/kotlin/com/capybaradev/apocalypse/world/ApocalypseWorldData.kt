package com.capybaradev.apocalypse.world

import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.saveddata.SavedData


class ApocalypseWorldData : SavedData() {

    var generated = false

    override fun save(
        tag: CompoundTag,
        provider: HolderLookup.Provider
    ): CompoundTag {

        tag.putBoolean("generated", generated)

        return tag
    }


    companion object {

        fun load(
            tag: CompoundTag,
            provider: HolderLookup.Provider
        ): ApocalypseWorldData {

            val data = ApocalypseWorldData()

            data.generated = tag.getBoolean("generated")

            return data
        }
    }
}