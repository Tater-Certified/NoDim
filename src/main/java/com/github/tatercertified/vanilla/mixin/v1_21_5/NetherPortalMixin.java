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

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@MCVer(min = "1.21.5")
@Mixin(NetherPortalBlock.class)
public class NetherPortalMixin {
    @Inject(
            method = {
                "entityInside(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)V", // Mojmap
                "method_9548(Lnet/minecraft/class_2680;Lnet/minecraft/class_1937;Lnet/minecraft/class_2338;Lnet/minecraft/class_1297;)V" // Intermediary
            },
            at = @At("HEAD"),
            cancellable = true,
            require = 0,
            expect = 1)
    private void nodim$checkIfNetherIsEnabled(
            BlockState blockState, Level level, BlockPos blockPos, Entity entity, CallbackInfo ci) {
        if (level instanceof ServerLevel serverWorld
                && serverWorld.getServer().getGameRules().getBoolean(NoDim.DISABLE_NETHER)) {
            ci.cancel();
        }
    }

    @Inject(
            method = {
                "entityInside(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/InsideBlockEffectApplier;)V", // Mojmap
                "method_9548(Lnet/minecraft/class_2680;Lnet/minecraft/class_1937;Lnet/minecraft/class_2338;Lnet/minecraft/class_1297;Lnet/minecraft/class_10774;)V" // Intermediary
            },
            at = @At("HEAD"),
            cancellable = true,
            require = 0,
            expect = 1)
    private void nodim$checkIfNetherIsEnabled(
            BlockState blockState,
            Level level,
            BlockPos blockPos,
            Entity entity,
            @Coerce Object insideBlockEffectApplier,
            CallbackInfo ci) {
        if (level instanceof ServerLevel serverWorld
                && serverWorld.getServer().getGameRules().getBoolean(NoDim.DISABLE_NETHER)) {
            ci.cancel();
        }
    }
}
