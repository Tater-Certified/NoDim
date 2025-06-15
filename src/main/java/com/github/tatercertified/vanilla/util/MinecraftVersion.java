/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.util;

import net.minecraft.DetectedVersion;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class MinecraftVersion {
    public static String getVersion() {
        // 1.19.4 and Above
        try {
            Field builtIn = DetectedVersion.class.getField("field_25319");
            Method version = builtIn.getType().getDeclaredMethod("method_48019");
            return (String) version.invoke(builtIn.get(null));
        } catch (NoSuchFieldException
                | IllegalAccessException
                | NoSuchMethodException
                | InvocationTargetException ignored) {
        }
        // 1.19.3 - 1.18
        try {
            Field builtIn = DetectedVersion.class.getField("field_25319");
            Method version = builtIn.getType().getDeclaredMethod("getName");
            return (String) version.invoke(builtIn.get(null));
        } catch (NoSuchFieldException
                | NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException ignored) {
        }
        // 1.14 - 1.17.1
        try {
            Method tryGetVersion = DetectedVersion.class.getMethod("method_16672");
            var worldVer = tryGetVersion.invoke(null);
            Method getName = worldVer.getClass().getDeclaredMethod("getName");
            return (String) getName.invoke(worldVer);
        } catch (NoSuchMethodException
                | InvocationTargetException
                | IllegalAccessException ignored) {
        }

        // Unknown Version
        throw new RuntimeException("No Valid Minecraft Version Found");
    }

    public static boolean isBetween(String inputVer, String min, String max) {
        return isBetween(getMinor(inputVer), getPatch(inputVer), min, max);
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

        if (inputMinor == maxMinor || inputMinor == minMinor) {

            int minPatch = getPatch(min);
            if (inputPatch < minPatch) {
                return false;
            }

            int maxPatch = getPatch(max);
            return inputPatch <= maxPatch;

        } else {
            return true;
        }
    }

    public static boolean isNewerThan(String inputVer, String compare) {
        int minor = getMinor(inputVer);
        int compareMinor = getMinor(compare);

        if (minor > compareMinor) {
            return true;

        } else if (minor == compareMinor) {
            int patch = getPatch(inputVer);
            int comparePatch = getPatch(compare);

            return patch > comparePatch;

        } else {
            return false;
        }
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
}
