package com.capybaradev.apocalypse.command

import com.capybaradev.apocalypse.world.HighwayMapGenerator
import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.neoforged.neoforge.event.RegisterCommandsEvent
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber

@EventBusSubscriber
object ModCommands {

    @SubscribeEvent
    fun registerCommands(event: RegisterCommandsEvent) {

        event.dispatcher.register(
            Commands.literal("highway")
                .requires { it.hasPermission(2) }
                .executes { context ->

                    val player = context.source.player

                    context.source.sendSuccess(
                        { net.minecraft.network.chat.Component.literal("Highway command executed") },
                        false
                    )
                    context.source.sendSuccess({ net.minecraft.network.chat.Component.literal(HighwayMapGenerator.nodes.toString()) },true)

                    context.source.sendSuccess({ net.minecraft.network.chat.Component.literal(HighwayMapGenerator.roads.toString()) },true)

                    Command.SINGLE_SUCCESS
                }
        )
    }
}