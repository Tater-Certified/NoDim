/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.util;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.mojang.math.MethodsReturnNonnullByDefault;

import org.objectweb.asm.tree.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class MinecraftVersion {
    private static String cachedVersion;

    public static String getVersion() {
        if (cachedVersion == null) {
            cachedVersion = getMinecraftVersion();
        }

        return cachedVersion;
    }

    public static boolean isBetween(int inputMinor, int inputPatch, String min, String max) {
        int minMinor = getMinor(min);
        if (inputMinor < minMinor) {
            return false;
        }

        int maxMinor = getMinor(max);
        if (inputMinor > maxMinor) {
            return false;
        }

        if (inputMinor == minMinor) {
            int minPatch = getPatch(min);
            if (inputPatch < minPatch) {
                return false;
            }
        }

        if (inputMinor == maxMinor) {
            int maxPatch = getPatch(max);
            return inputPatch <= maxPatch;
        }

        return true;
    }

    public static int getMinor(String version) {
        String[] split = version.split("\\.");
        return Integer.parseInt(split[1]);
    }

    public static int getPatch(String version) {
        String[] split = version.split("\\.");
        if (split.length == 3) {
            return Integer.parseInt(split[2]);
        } else {
            return 0;
        }
    }

    private static String getMinecraftVersion() {
        try {
            SerializedMCVer ver;
            // Only load a useless class
            try (InputStream inputStream =
                    MethodsReturnNonnullByDefault.class.getResourceAsStream("/version.json")) {
                if (inputStream == null) {
                    // Failed to find a valid version
                    throw new RuntimeException("Failed to locate Minecraft Version");
                }

                try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
                    Gson gson = new Gson();
                    ver = gson.fromJson(inputStreamReader, SerializedMCVer.class);
                }
            }

            return ver.id();
        } catch (JsonParseException | IOException exception) {
            throw new IllegalStateException("Game version information is corrupt", exception);
        }
    }
}
