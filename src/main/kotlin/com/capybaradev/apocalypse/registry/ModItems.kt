package com.capybaradev.apocalypse.registry

import com.capybaradev.apocalypse.Apocalypse
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModItems {

    val ITEMS: DeferredRegister.Items =
        DeferredRegister.createItems(Apocalypse.Companion.MOD_ID)

    val RUBY_BLOCK_ITEM: DeferredItem<BlockItem> =
        ITEMS.register(
            "ruby_block",
            Supplier {
                BlockItem(
                    ModBlocks.RUBY_BLOCK.get(),
                    Item.Properties()
                )
            }
        )
}