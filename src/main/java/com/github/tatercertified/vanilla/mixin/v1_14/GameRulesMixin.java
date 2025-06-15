/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.mixin.v1_14;

import com.github.tatercertified.vanilla.annotation.MCVer;

import net.minecraft.world.level.GameRules;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@MCVer(min = "1.14", max = "1.15.2")
@Mixin(GameRules.class)
public interface GameRulesMixin {
    @Invoker("method_8359")
    static <T extends GameRules.Value<T>> GameRules.Key<T> register(
            String string, GameRules.Type<T> type) {
        throw new AssertionError();
    }
}
