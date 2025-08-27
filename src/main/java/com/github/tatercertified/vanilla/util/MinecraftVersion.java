/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public final class MinecraftVersion {
    private static String cachedVersion;

    public static String parseVersion() {
        URL url = ClassLoader.getSystemResource("version.json");
        String version;

        try (Reader reader = new InputStreamReader(url.openStream())) {
            // Using deprecated methods since Minecraft 1.14 uses an old Gson version
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(reader);
            JsonObject obj = element.getAsJsonObject();
            version = obj.get("name").getAsString();
        } catch (IOException e) {
            throw new RuntimeException("No Valid Minecraft Version Found");
        }

        return version;
    }

    public static String getVersion() {
        if (cachedVersion != null) {
            return cachedVersion;
        } else {
            cachedVersion = LoaderUtil.getMCVersion();
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
            return inputPatch >= minPatch;
        } else if (inputMinor == maxMinor) {
            int maxPatch = getPatch(max);
            return inputPatch <= maxPatch;
        } else {
            return true;
        }
    }

    /*
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
     */

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
