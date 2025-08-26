/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.forge;

import com.github.tatercertified.vanilla.util.LoaderMappingsProvider;
import com.github.tatercertified.vanilla.util.Mapping;
import com.github.tatercertified.vanilla.util.MinecraftVersion;

public class ForgeMappingsProvider implements LoaderMappingsProvider {
    @Override
    public Mapping getMappings() {
        return Mapping.SRG;
    }

    @Override
    public String getMCVersion() {
        return MinecraftVersion.parseVersion();
    }
}
