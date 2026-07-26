package com.capybaradev.apocalypse.registry

import com.capybaradev.apocalypse.Apocalypse
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModBlocks {

    val BLOCKS: DeferredRegister.Blocks =
        DeferredRegister.createBlocks(Apocalypse.Companion.MOD_ID)

    val RUBY_BLOCK: DeferredBlock<Block> =
        BLOCKS.register(
            "ruby_block",
            Supplier {
                Block(
                    BlockBehaviour.Properties.of()
                        .mapColor(MapColor.COLOR_RED)
                        .strength(5f)
                        .sound(SoundType.METAL)
                        .requiresCorrectToolForDrops()
                )
            }
        )
}