/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla;

import com.github.tatercertified.vanilla.mixin.v1_14.BooleanValueMojMixin;
import com.github.tatercertified.vanilla.mixin.v1_16.GameRulesMojMixin;
import com.github.tatercertified.vanilla.mixin.v1_16.GameRulesSRGMixin;
import com.github.tatercertified.vanilla.util.LoaderUtil;
import com.github.tatercertified.vanilla.util.MinecraftVersion;

import net.minecraft.world.level.GameRules;

public final class NoDimGameRules {

    public static void registerGamerules() {
        String mcVer = MinecraftVersion.getVersion();
        if (MinecraftVersion.isNewerThan(mcVer, "1.15.2")) {
            switch (LoaderUtil.getMappings()) {
                case SRG -> {
                    NoDim.DISABLE_END =
                            GameRulesSRGMixin.register(
                                    "disableEnd",
                                    GameRules.Category.MISC,
                                    BooleanValueMojMixin.create(false));
                    NoDim.DISABLE_NETHER =
                            GameRulesSRGMixin.register(
                                    "disableNether",
                                    GameRules.Category.MISC,
                                    BooleanValueMojMixin.create(false));
                    NoDim.DISABLE_GATEWAY =
                            GameRulesSRGMixin.register(
                                    "disableGateway",
                                    GameRules.Category.MISC,
                                    BooleanValueMojMixin.create(false));
                }
                case Mojmap -> {
                    NoDim.DISABLE_END =
                            GameRulesMojMixin.register(
                                    "disableEnd",
                                    GameRules.Category.MISC,
                                    BooleanValueMojMixin.create(false));
                    NoDim.DISABLE_NETHER =
                            GameRulesMojMixin.register(
                                    "disableNether",
                                    GameRules.Category.MISC,
                                    BooleanValueMojMixin.create(false));
                    NoDim.DISABLE_GATEWAY =
                            GameRulesMojMixin.register(
                                    "disableGateway",
                                    GameRules.Category.MISC,
                                    BooleanValueMojMixin.create(false));
                }
            }
        } else {
            switch (LoaderUtil.getMappings()) {
                case SRG -> {
                    NoDim.DISABLE_END =
                            com.github.tatercertified.vanilla.mixin.v1_14.GameRulesSRGMixin
                                    .register("disableEnd", BooleanValueMojMixin.create(false));
                    NoDim.DISABLE_NETHER =
                            com.github.tatercertified.vanilla.mixin.v1_14.GameRulesSRGMixin
                                    .register("disableNether", BooleanValueMojMixin.create(false));
                    NoDim.DISABLE_GATEWAY =
                            com.github.tatercertified.vanilla.mixin.v1_14.GameRulesSRGMixin
                                    .register("disableGateway", BooleanValueMojMixin.create(true));
                }
                case Mojmap -> {
                    NoDim.DISABLE_END =
                            com.github.tatercertified.vanilla.mixin.v1_14.GameRulesMojMixin
                                    .register("disableEnd", BooleanValueMojMixin.create(false));
                    NoDim.DISABLE_NETHER =
                            com.github.tatercertified.vanilla.mixin.v1_14.GameRulesMojMixin
                                    .register("disableNether", BooleanValueMojMixin.create(false));
                    NoDim.DISABLE_GATEWAY =
                            com.github.tatercertified.vanilla.mixin.v1_14.GameRulesMojMixin
                                    .register("disableGateway", BooleanValueMojMixin.create(true));
                }
            }
        }
    }
}
