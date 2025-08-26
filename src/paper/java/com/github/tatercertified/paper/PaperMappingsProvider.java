/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.paper;

import com.github.tatercertified.vanilla.util.LoaderMappingsProvider;
import com.github.tatercertified.vanilla.util.Mapping;

import io.papermc.paper.ServerBuildInfo;

public class PaperMappingsProvider implements LoaderMappingsProvider {
    @Override
    public Mapping getMappings() {
        return Mapping.Mojmap;
    }

    @Override
    public String getMCVersion() {
        return ServerBuildInfo.buildInfo().minecraftVersionName();
    }
}
