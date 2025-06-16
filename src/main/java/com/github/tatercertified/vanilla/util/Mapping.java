/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.util;

public enum Mapping {
    Mojmap,
    SRG,
    Spigot;

    public static Mapping fromString(String str) {
        for (Mapping mapping : values()) {
            if (mapping.name().equals(str)) {
                return mapping;
            }
        }
        return null;
    }
}
