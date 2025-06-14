/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.mixin;

import com.github.tatercertified.vanilla.NoDim;
import com.github.tatercertified.vanilla.annotation.MCVer;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@MCVer(min = "1.12.2", max = "1.20.6")
@Mixin(TheEndGatewayBlockEntity.class)
public class TheGatewayPortalMixin {
    @Inject(method = "teleportTick", at = @At("HEAD"), cancellable = true)
    private static void nodim$checkIfGatewayIsEnabled(
            Level level,
            BlockPos pos,
            BlockState state,
            TheEndGatewayBlockEntity blockEntity,
            CallbackInfo ci) {
        if (level instanceof ServerLevel serverWorld
                && serverWorld.getServer().getGameRules().getBoolean(NoDim.DISABLE_GATEWAY)) {
            ci.cancel();
        }
    }
}
