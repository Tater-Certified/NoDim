/**
 * Copyright (c) 2026 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.nodim.vanilla.mixin;

import com.moulberry.mixinconstraints.annotations.IfMinecraftVersion;

import net.minecraft.world.level.GameRules;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRules.BooleanValue.class)
public interface BooleanValueMixin {

    @IfMinecraftVersion(minVersion = "1.14.3")
    @Invoker("create")
    static GameRules.Type<GameRules.BooleanValue> create(boolean bl) {
        throw new AssertionError();
    }
}
