/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.mixin;

import net.minecraft.world.level.GameRules;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRules.class)
public interface GameRulesMixin {
    @Invoker("register")
    static <T extends GameRules.Value<T>> GameRules.Key<T> register(
            String string, GameRules.Category category, GameRules.Type<T> type) {
        throw new AssertionError();
    }
}
