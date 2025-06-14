/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.mixin;

import com.github.tatercertified.vanilla.NoDim;
import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EndPortalBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndPortalBlock.class)
public class EndPortalMixin {
    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    private void nodim$checkIfEndIsEnabled(CallbackInfo ci, @Local(argsOnly = true) Level level) {
        if (level instanceof ServerLevel serverWorld
                && serverWorld.getGameRules().getBoolean(NoDim.DISABLE_END)) {
            ci.cancel();
        }
    }
}
