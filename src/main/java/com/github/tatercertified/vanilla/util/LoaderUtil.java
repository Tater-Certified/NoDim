/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.util;

import org.spongepowered.asm.service.MixinService;

import java.io.IOException;

public class LoaderUtil {
    public static final Mapping MAPPINGS = getMappings();

    private static Mapping getMappings() {
        if (checkForClass("io.papermc.paperclip.Paperclip")
                || checkForClass("org.spigotmc.CustomTimingsHandler")) {
            return Mapping.Mojmap;
        } else if (checkForClass("net.fabricmc.loader.api.FabricLoader")
                || checkForClass("net.quiltservertools.quilt.api.QuiltServer")) {
            return Mapping.Intermediary;
        } else if (checkForClass("net.neoforged.neoforge.common.NeoForge")
                || checkForClass("net.minecraftforge.fml.loading.FMLLoader")) {
            return Mapping.SRG;
        } else if (checkForClass("org.spongepowered.api.Sponge")) {
            return Mapping.Mojmap;
        } else {
            throw new RuntimeException("Unknown loader detected; Failing to load NoDim");
        }
    }

    private static boolean checkForClass(String className) {
        try {
            MixinService.getService().getBytecodeProvider().getClassNode(className);
        } catch (ClassNotFoundException | IOException ignored) {
            return false;
        }
        return true;
    }
}
