package com.capybaradev.apocalypse.registry

import com.mojang.serialization.Codec
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries
import java.util.function.Supplier


object ModAttachments {

    val ATTACHMENTS =
        DeferredRegister.create(
            NeoForgeRegistries.ATTACHMENT_TYPES,
            "apocalypse"
        )


    val HIGHWAY_GENERATED = ATTACHMENTS.register(
        "highway_generated",
        Supplier {
            AttachmentType.builder<Boolean>(java.util.function.Supplier { false })
                .serialize(Codec.BOOL)
                .build()
        }
    )
}