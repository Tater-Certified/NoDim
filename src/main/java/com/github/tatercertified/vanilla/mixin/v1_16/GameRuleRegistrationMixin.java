/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.mixin.v1_16;

import com.github.tatercertified.vanilla.NoDim;
import com.github.tatercertified.vanilla.annotation.MCVer;
import com.github.tatercertified.vanilla.mixin.v1_14_3.BooleanValueMixin;

import net.minecraft.world.level.GameRules;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@MCVer(min = "1.16")
@Mixin(GameRules.class)
public abstract class GameRuleRegistrationMixin {
    @Shadow
    private static <T extends GameRules.Value<T>> GameRules.Key<T> register(
            String name, GameRules.Category category, GameRules.Type<T> type) {
        return null;
    }

    @Inject(
            method = "<clinit>",
            at =
                    @At(
                            value = "FIELD",
                            target =
                                    "Lnet/minecraft/world/level/GameRules;RULE_DOFIRETICK:Lnet/minecraft/world/level/GameRules$Key;"))
    private static void nodim$registerGameRules(CallbackInfo ci) {
        NoDim.DISABLE_END =
                register("disableEnd", GameRules.Category.MISC, BooleanValueMixin.create(false));
        NoDim.DISABLE_GATEWAY =
                register(
                        "disableGateway", GameRules.Category.MISC, BooleanValueMixin.create(false));
        NoDim.DISABLE_NETHER =
                register("disableNether", GameRules.Category.MISC, BooleanValueMixin.create(false));
    }
}
