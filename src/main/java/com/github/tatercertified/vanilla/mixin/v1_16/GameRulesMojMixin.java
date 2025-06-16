/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.mixin.v1_16;

import com.github.tatercertified.vanilla.annotation.MCVer;
import com.github.tatercertified.vanilla.annotation.Map;
import com.github.tatercertified.vanilla.util.Mapping;

import net.minecraft.world.level.GameRules;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Map(mapping = Mapping.Mojmap)
@MCVer(min = "1.16")
@Mixin(GameRules.class)
public interface GameRulesMojMixin {
    @Invoker("register") // Mojmap
    static <T extends GameRules.Value<T>> GameRules.Key<T> register(
            String string, GameRules.Category category, GameRules.Type<T> type) {
        throw new AssertionError();
    }
}
