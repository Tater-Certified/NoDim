/**
 * Copyright (c) 2026 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.mixin;

import com.github.tatercertified.vanilla.NoDim;
import com.moulberry.mixinconstraints.annotations.IfMinecraftVersion;

import net.minecraft.world.level.GameRules;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRules.class)
public class GameRuleRegistrationMixin {

    // Old
    @IfMinecraftVersion(maxVersion = "1.15.2")
    @Shadow(aliases = {"method_8359", "m_46189_"})
    private static <T extends GameRules.Value<T>> GameRules.Key<T> register(
            String string, GameRules.Type<T> type) {
        return null;
    }

    // New
    @IfMinecraftVersion(minVersion = "1.16.0")
    @Shadow(aliases = "method_8359")
    private static <T extends GameRules.Value<T>> GameRules.Key<T> register(
            String name, GameRules.Category category, GameRules.Type<T> type) {
        return null;
    }

    @IfMinecraftVersion(maxVersion = "1.15.2")
    @Inject(
            method = "<clinit>",
            at = {
                @At(
                        value = "FIELD",
                        target =
                                "Lnet/minecraft/world/class_1928;field_19387:Lnet/minecraft/world/class_1928$class_4313;",
                        opcode = Opcodes.PUTSTATIC), // Inter
                @At(
                        value = "FIELD",
                        target =
                                "Lnet/minecraft/world/level/GameRules;RULE_DOFIRETICK:Lnet/minecraft/world/level/GameRules$Key;",
                        opcode = Opcodes.PUTSTATIC), // Mojmap
                @At(
                        value = "FIELD",
                        target =
                                "Lnet/minecraft/world/GameRules;RULE_DOFIRETICK:Lnet/minecraft/world/GameRules$RuleKey;",
                        opcode = Opcodes.PUTSTATIC), // SRG
            },
            require = 0)
    private static void nodim$registerGameRules_1_14_3(CallbackInfo ci) {
        NoDim.DISABLE_END = register("disableEnd", BooleanValueMixin.create(false));
        NoDim.DISABLE_GATEWAY = register("disableGateway", BooleanValueMixin.create(false));
        NoDim.DISABLE_NETHER = register("disableNether", BooleanValueMixin.create(false));
    }

    @IfMinecraftVersion(minVersion = "1.16.0")
    @Inject(
            method = "<clinit>",
            at = {
                @At(
                        value = "FIELD",
                        target =
                                "Lnet/minecraft/world/class_1928;field_19387:Lnet/minecraft/world/class_1928$class_4313;",
                        opcode = Opcodes.PUTSTATIC), // Inter
                @At(
                        value = "FIELD",
                        target =
                                "Lnet/minecraft/world/level/GameRules;RULE_DOFIRETICK:Lnet/minecraft/world/level/GameRules$Key;",
                        opcode = Opcodes.PUTSTATIC), // Mojmap
                @At(
                        value = "FIELD",
                        target =
                                "Lnet/minecraft/world/GameRules;RULE_DOFIRETICK:Lnet/minecraft/world/GameRules$RuleKey;",
                        opcode = Opcodes.PUTSTATIC), // SRG
            },
            require = 0)
    private static void nodim$registerGameRules_1_16(CallbackInfo ci) {
        NoDim.DISABLE_END =
                register("disableEnd", GameRules.Category.MISC, BooleanValueMixin.create(false));
        NoDim.DISABLE_GATEWAY =
                register(
                        "disableGateway", GameRules.Category.MISC, BooleanValueMixin.create(false));
        NoDim.DISABLE_NETHER =
                register("disableNether", GameRules.Category.MISC, BooleanValueMixin.create(false));
    }
}
