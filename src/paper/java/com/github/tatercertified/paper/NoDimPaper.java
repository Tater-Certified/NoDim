/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.paper;

import com.github.tatercertified.vanilla.NoDim;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class NoDimPaper extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        NoDim.init();
    }
}
