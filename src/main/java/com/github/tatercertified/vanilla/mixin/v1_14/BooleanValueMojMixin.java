/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.mixin.v1_14;

import com.github.tatercertified.vanilla.annotation.MCVer;
import com.github.tatercertified.vanilla.annotation.Map;
import com.github.tatercertified.vanilla.util.Mapping;

import net.minecraft.world.level.GameRules;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Map(mapping = Mapping.Mojmap)
@MCVer(min = "1.14")
@Mixin(GameRules.BooleanValue.class)
public interface BooleanValueMojMixin {
    @Invoker("create") // Mojmap
    static GameRules.Type<GameRules.BooleanValue> create(boolean bl) {
        throw new AssertionError();
    }
}
