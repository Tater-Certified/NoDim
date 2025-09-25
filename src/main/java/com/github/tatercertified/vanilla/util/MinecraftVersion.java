/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.util;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.service.MixinService;

import java.io.IOException;

public final class MinecraftVersion {
    private static String cachedVersion;

    public static String getVersion() {
        if (cachedVersion == null) {
            cachedVersion = getMinecraftVersion();
            System.out.print("VERSION: " + cachedVersion);
        }

        return cachedVersion;
    }

    public static boolean isBetween(int inputMinor, int inputPatch, String min, String max) {
        int minMinor = getMinor(min);
        if (inputMinor < minMinor) {
            return false;
        }

        int maxMinor = getMinor(max);
        if (inputMinor > maxMinor) {
            return false;
        }

        if (inputMinor == minMinor) {
            int minPatch = getPatch(min);
            if (inputPatch < minPatch) {
                return false;
            }
        }

        if (inputMinor == maxMinor) {
            int maxPatch = getPatch(max);
            return inputPatch <= maxPatch;
        }

        return true;
    }

    public static int getMinor(String version) {
        String[] split = version.split("\\.");
        return Integer.parseInt(split[1]);
    }

    public static int getPatch(String version) {
        String[] split = version.split("\\.");
        if (split.length == 3) {
            return Integer.parseInt(split[2]);
        } else {
            return 0;
        }
    }

    public static String getMinecraftVersion() {
        String version;

        // Try 1.17=<
        try {
            version =
                    switch (LoaderUtil.MAPPINGS) {
                        case Intermediary -> {
                            ClassNode node =
                                    MixinService.getService()
                                            .getBytecodeProvider()
                                            .getClassNode("net.minecraft.class_155");
                            yield getStaticStringValue(node, "field_29733");
                        }
                        case Mojmap, Spigot -> {
                            ClassNode node =
                                    MixinService.getService()
                                            .getBytecodeProvider()
                                            .getClassNode("net.minecraft.SharedConstants");
                            yield getStaticStringValue(node, "VERSION_STRING");
                        }
                        case SRG -> {
                            ClassNode node =
                                    MixinService.getService()
                                            .getBytecodeProvider()
                                            .getClassNode("net.minecraft.src.C_5285_");
                            yield getStaticStringValue(node, "f_142952_");
                        }
                    };
        } catch (ClassNotFoundException | IOException e) {
            // Try again
            try {
                ClassNode node =
                        MixinService.getService()
                                .getBytecodeProvider()
                                .getClassNode("net.minecraft.SharedConstants");
                version = getStaticStringValue(node, "VERSION_STRING");
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

        // Try <1.17
        if (version == null) {
            try {
                version =
                        switch (LoaderUtil.MAPPINGS) {
                            // Try 1.17=<
                            case Intermediary -> {
                                ClassNode node =
                                        MixinService.getService()
                                                .getBytecodeProvider()
                                                .getClassNode("net.minecraft.class_3797");
                                yield getAssignedStringToField(node, "<init>", "field_16733");
                            }
                            case Mojmap, Spigot -> {
                                ClassNode node =
                                        MixinService.getService()
                                                .getBytecodeProvider()
                                                .getClassNode("net.minecraft.DetectedVersion");
                                yield getAssignedStringToField(node, "<init>", "name");
                            }
                            case SRG -> {
                                ClassNode node =
                                        MixinService.getService()
                                                .getBytecodeProvider()
                                                .getClassNode(
                                                        "net.minecraft.util.MinecraftVersion");
                                yield getAssignedStringToField(node, "<init>", "field_214960_c");
                            }
                        };
            } catch (ClassNotFoundException | IOException e) {
                // Try again
                try {
                    ClassNode node =
                            MixinService.getService()
                                    .getBytecodeProvider()
                                    .getClassNode("net.minecraft.DetectedVersion");
                    version = getAssignedStringToField(node, "<init>", "name");
                } catch (ClassNotFoundException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return version;
    }

    private static String getStaticStringValue(ClassNode classNode, String fieldName) {
        for (FieldNode field : classNode.fields) {
            if (field.name.equals(fieldName) && field.desc.equals("Ljava/lang/String;")) {
                return (String) field.value;
            }
        }
        return null;
    }

    private static String getAssignedStringToField(
            ClassNode classNode, String methodName, String fieldName) {
        for (MethodNode method : classNode.methods) {
            if (method.name.equals(methodName)) {
                AbstractInsnNode insn = method.instructions.getFirst();
                while (insn != null) {
                    if (insn.getOpcode() == Opcodes.PUTFIELD) {
                        FieldInsnNode fieldInsn = (FieldInsnNode) insn;
                        if (fieldInsn.name.equals(fieldName)
                                && fieldInsn.desc.equals("Ljava/lang/String;")) {
                            AbstractInsnNode prev = fieldInsn.getPrevious();
                            if (prev.getOpcode() == Opcodes.LDC) {
                                LdcInsnNode ldc = (LdcInsnNode) prev;
                                if (ldc.cst instanceof String) {
                                    return (String) ldc.cst;
                                }
                            }
                        }
                    }
                    insn = insn.getNext();
                }
            }
        }
        return null;
    }
}
