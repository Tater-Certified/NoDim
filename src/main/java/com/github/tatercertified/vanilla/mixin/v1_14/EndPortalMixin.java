/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.mixin.v1_14;

import com.github.tatercertified.vanilla.NoDim;
import com.github.tatercertified.vanilla.annotation.MCVer;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EndPortalBlock;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@MCVer(min = "1.14", max = "1.21.4")
@Mixin(EndPortalBlock.class)
public class EndPortalMixin {
    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    private void nodim$checkIfEndIsEnabled(
            BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (level instanceof ServerLevel serverWorld
                && serverWorld.getServer().getGameRules().getBoolean(NoDim.DISABLE_END)) {
            ci.cancel();
        }
    }
}
