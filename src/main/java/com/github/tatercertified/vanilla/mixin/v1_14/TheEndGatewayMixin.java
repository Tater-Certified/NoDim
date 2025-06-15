/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.mixin.v1_14;

import com.github.tatercertified.vanilla.NoDim;
import com.github.tatercertified.vanilla.annotation.MCVer;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@MCVer(min = "1.14", max = "1.16.5")
@Mixin(TheEndGatewayBlockEntity.class)
public class TheEndGatewayMixin {
    @Inject(method = "method_16896", at = @At("HEAD"), cancellable = true)
    private void nodim$checkIfGatewayIsEnabled(CallbackInfo ci) {
        Level level = ((TheEndGatewayBlockEntity) (Object) this).getLevel();
        if (level instanceof ServerLevel
                && level.getServer().getGameRules().getBoolean(NoDim.DISABLE_GATEWAY)) {
            ci.cancel();
        }
    }
}
