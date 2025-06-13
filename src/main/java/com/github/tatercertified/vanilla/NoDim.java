/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla;

import com.github.tatercertified.vanilla.mixin.BooleanValueMixin;
import com.github.tatercertified.vanilla.mixin.GameRulesMixin;

import net.minecraft.world.level.GameRules;

public class NoDim {
    public static final String MOD_ID = "nodim";

    public static final GameRules.Key<GameRules.BooleanValue> DISABLE_END =
            GameRulesMixin.register(
                    "disableEnd", GameRules.Category.MISC, BooleanValueMixin.create(false));
    public static final GameRules.Key<GameRules.BooleanValue> DISABLE_NETHER =
            GameRulesMixin.register(
                    "disableNether", GameRules.Category.MISC, BooleanValueMixin.create(false));
    public static final GameRules.Key<GameRules.BooleanValue> DISABLE_GATEWAY =
            GameRulesMixin.register(
                    "disableGateway", GameRules.Category.MISC, BooleanValueMixin.create(true));

    public static void init() {}
}
