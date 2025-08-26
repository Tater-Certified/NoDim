/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.util;

import java.util.ServiceLoader;

public class LoaderUtil {
    private static final LoaderMappingsProvider provider;

    static {
        provider =
                ServiceLoader.load(LoaderMappingsProvider.class)
                        .findFirst()
                        .orElseThrow(
                                () -> new IllegalStateException("No LoaderMappingsProvider found"));
    }

    public static Mapping getMappings() {
        return provider.getMappings();
    }

    public static String getMCVersion() {
        return provider.getMCVersion();
    }
}
