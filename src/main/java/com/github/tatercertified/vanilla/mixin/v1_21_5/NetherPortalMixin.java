/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.mixin.v1_21_5;

import com.github.tatercertified.vanilla.NoDim;
import com.github.tatercertified.vanilla.annotation.MCVer;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@MCVer(min = "1.21.5")
@Mixin(NetherPortalBlock.class)
public class NetherPortalMixin {
    @Dynamic
    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    private void nodim$checkIfEndIsEnabled(
            BlockState blockState,
            Level level,
            BlockPos blockPos,
            Entity entity,
            Object insideBlockEffectApplier,
            CallbackInfo ci) {
        if (level instanceof ServerLevel serverWorld
                && serverWorld.getGameRules().getBoolean(NoDim.DISABLE_NETHER)) {
            ci.cancel();
        }
    }
}
