/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla;

import net.minecraft.world.level.GameRules;

public class NoDim {
    public static final String MOD_ID = "nodim";
    public static GameRules.Key<GameRules.BooleanValue> DISABLE_END;
    public static GameRules.Key<GameRules.BooleanValue> DISABLE_NETHER;
    public static GameRules.Key<GameRules.BooleanValue> DISABLE_GATEWAY;

    public static void init() {
        NoDimGameRules.registerGamerules();
    }
}
