/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla;

import com.github.tatercertified.vanilla.mixin.v1_14.BooleanValueMixin;
import com.github.tatercertified.vanilla.mixin.v1_14.GameRulesMixin;
import com.github.tatercertified.vanilla.util.MinecraftVersion;

import net.minecraft.world.level.GameRules;

public class NoDim {
    public static final String MOD_ID = "nodim";

    public static GameRules.Key<GameRules.BooleanValue> DISABLE_END;
    public static GameRules.Key<GameRules.BooleanValue> DISABLE_NETHER;
    public static GameRules.Key<GameRules.BooleanValue> DISABLE_GATEWAY;

    static {
        String mcVer = MinecraftVersion.getVersion();
        if (MinecraftVersion.isNewerThan(mcVer, "1.15.2")) {
            DISABLE_END =
                    com.github.tatercertified.vanilla.mixin.v1_16.GameRulesMixin.register(
                            "disableEnd", GameRules.Category.MISC, BooleanValueMixin.create(false));
            DISABLE_NETHER =
                    com.github.tatercertified.vanilla.mixin.v1_16.GameRulesMixin.register(
                            "disableNether",
                            GameRules.Category.MISC,
                            BooleanValueMixin.create(false));
            DISABLE_GATEWAY =
                    com.github.tatercertified.vanilla.mixin.v1_16.GameRulesMixin.register(
                            "disableGateway",
                            GameRules.Category.MISC,
                            BooleanValueMixin.create(true));
        } else {
            DISABLE_END = GameRulesMixin.register("disableEnd", BooleanValueMixin.create(false));
            DISABLE_NETHER =
                    GameRulesMixin.register("disableNether", BooleanValueMixin.create(false));
            DISABLE_GATEWAY =
                    GameRulesMixin.register("disableGateway", BooleanValueMixin.create(true));
        }
    }

    public static void init() {}
}
