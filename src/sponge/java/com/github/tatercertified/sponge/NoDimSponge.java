/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.sponge;

import com.github.tatercertified.vanilla.NoDim;
import com.google.inject.Inject;

import org.apache.logging.log4j.Logger;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

@Plugin(NoDim.MOD_ID)
public class NoDimSponge {

    @Inject
    public NoDimSponge(Logger logger, PluginContainer pluginContainer) {
        NoDim.init();
    }
}
