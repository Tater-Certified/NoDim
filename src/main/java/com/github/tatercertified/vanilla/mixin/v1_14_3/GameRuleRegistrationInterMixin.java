/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.mixin.v1_14_3;

import com.github.tatercertified.vanilla.NoDim;
import com.github.tatercertified.vanilla.annotation.MCVer;
import com.github.tatercertified.vanilla.annotation.Map;
import com.github.tatercertified.vanilla.util.Mapping;

import net.minecraft.world.level.GameRules;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Map(mapping = Mapping.Intermediary)
@MCVer(min = "1.14.3", max = "1.15.2")
@Mixin(GameRules.class)
public class GameRuleRegistrationInterMixin {
    @Shadow
    private static <T extends GameRules.Value<T>> GameRules.Key<T> method_8359(
            String string, GameRules.Type<T> type) {
        return null;
    }

    @Inject(
            method = "<clinit>",
            at =
                    @At(
                            value = "FIELD",
                            target =
                                    "Lnet/minecraft/world/level/GameRules;RULE_DOFIRETICK:Lnet/minecraft/world/level/GameRules$Key;"),
            require = 0)
    private static void nodim$registerGameRulesIntermediary(CallbackInfo ci) {
        NoDim.DISABLE_END = method_8359("disableEnd", BooleanValueMixin.create(false));
        NoDim.DISABLE_GATEWAY = method_8359("disableGateway", BooleanValueMixin.create(false));
        NoDim.DISABLE_NETHER = method_8359("disableNether", BooleanValueMixin.create(false));
    }
}
