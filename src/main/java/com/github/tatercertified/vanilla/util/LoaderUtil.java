/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.util;

import org.spongepowered.asm.service.MixinService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoaderUtil {
    private static final LoaderMappingsProvider PROVIDER = getLoader();

    public static Mapping getMappings() {
        return PROVIDER.getMappings();
    }

    public static String getMCVersion() {
        return PROVIDER.getMCVersion();
    }

    public static LoaderMappingsProvider getLoader() {
        if (checkForClass("io.papermc.paperclip.Paperclip")) {
            return new LoaderMappingsProvider() {
                @Override
                public Mapping getMappings() {
                    return Mapping.Mojmap;
                }

                @Override
                public String getMCVersion() {
                    return MinecraftVersion.parseVersion();
                }
            };
        } else if (checkForClass("net.fabricmc.loader.api.FabricLoader")
                || checkForClass("net.quiltservertools.quilt.api.QuiltServer")) {
            return new LoaderMappingsProvider() {
                @Override
                public Mapping getMappings() {
                    return Mapping.Intermediary;
                }

                @Override
                public String getMCVersion() {
                    return MinecraftVersion.parseVersion();
                }
            };
        } else if (checkForClass("net.neoforged.neoforge.common.NeoForge")
                || checkForClass("net.minecraftforge.fml.loading.FMLLoader")) {
            return new LoaderMappingsProvider() {
                @Override
                public Mapping getMappings() {
                    return Mapping.SRG;
                }

                @Override
                public String getMCVersion() {
                    return MinecraftVersion.parseVersion();
                }
            };
        } else if (checkForClass("org.spongepowered.api.Sponge")) {
            return new LoaderMappingsProvider() {
                @Override
                public Mapping getMappings() {
                    return Mapping.Mojmap;
                }

                @Override
                public String getMCVersion() {
                    try {
                        URL location =
                                this.getClass().getProtectionDomain().getCodeSource().getLocation();

                        String jarPathString =
                                location.toURI().getSchemeSpecificPart().replace("file:///", "");
                        jarPathString = jarPathString.substring(0, jarPathString.lastIndexOf('!'));

                        Path jarPath = Paths.get(jarPathString);

                        Path targetPath =
                                jarPath.getParent()
                                        .getParent()
                                        .getParent()
                                        .resolve("libraries/net/minecraft");

                        for (File file : targetPath.toFile().listFiles()) {
                            if (!"mappings".equals(file.getName())) {
                                return file.getName();
                            }
                        }

                    } catch (Exception e) {
                        throw new RuntimeException("Failed to get Minecraft Version from Sponge");
                    }
                    return null;
                }
            };
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
