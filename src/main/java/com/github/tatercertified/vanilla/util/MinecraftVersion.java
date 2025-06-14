/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.util;

import net.minecraft.SharedConstants;

import java.lang.reflect.Field;

public final class MinecraftVersion {
    public static String getVersion() {
        try {
            Field version = SharedConstants.class.getField("field_29733");
            return (String) version.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
