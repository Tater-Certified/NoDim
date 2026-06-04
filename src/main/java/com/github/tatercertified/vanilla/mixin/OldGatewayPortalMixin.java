/**
 * Copyright (c) 2026 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.mixin;

import com.github.tatercertified.vanilla.NoDim;
import com.moulberry.mixinconstraints.annotations.IfMinecraftVersion;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TheEndGatewayBlockEntity.class)
public class OldGatewayPortalMixin {
    @IfMinecraftVersion(minVersion = "1.17.0", maxVersion = "1.19.4")
    @Inject(
            method = {
                "teleportTick", // Mojmap
                "method_31702", // Intermediary
                "m_155844_" // SRG
            },
            at = @At("HEAD"),
            cancellable = true)
    private static void nodim$checkIfGatewayIsEnabled1_17(
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

    @IfMinecraftVersion(maxVersion = "1.16.5")
    @Inject(
            method = {
                "tick", // Mojmap
                "method_16896", // Intermediary
                "m_155107_" // SRG
            },
            at = @At("HEAD"),
            cancellable = true)
    private void nodim$checkIfGatewayIsEnabled1_14_3(CallbackInfo ci) {
        Level level = ((TheEndGatewayBlockEntity) (Object) this).getLevel();
        if (level instanceof ServerLevel
                && level.getServer().getGameRules().getBoolean(NoDim.DISABLE_GATEWAY)) {
            ci.cancel();
        }
    }
}
