/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.mixin;

import com.github.tatercertified.vanilla.NoDim;

import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRules;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRules.class)
public abstract class GameRuleRegistrationMixin {
    @Shadow
    private static GameRule<Boolean> registerBoolean(
            String name, GameRuleCategory category, boolean defaultValue) {
        return null;
    }

    @Inject(
            method = "<clinit>",
            at =
                    @At(
                            value = "FIELD",
                            target =
                                    "Lnet/minecraft/world/level/gamerules/GameRules;FIRE_SPREAD_RADIUS_AROUND_PLAYER:Lnet/minecraft/world/level/gamerules/GameRule;",
                            opcode = Opcodes.PUTSTATIC))
    private static void nodim$registerGameRules(CallbackInfo ci) {
        NoDim.DISABLE_END = registerBoolean("disable_end", GameRuleCategory.MISC, true);
        NoDim.DISABLE_NETHER = registerBoolean("disable_nether", GameRuleCategory.MISC, true);
        NoDim.DISABLE_GATEWAY = registerBoolean("disable_gateway", GameRuleCategory.MISC, true);
    }
}
