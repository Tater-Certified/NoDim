/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla.util;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.service.MixinService;

import java.io.IOException;
import java.util.Set;

public final class MinecraftVersion {
    private static String cachedVersion;

    public static String getVersion() {
        if (cachedVersion == null) {
            cachedVersion = getMinecraftVersion(false);
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

    public static String getMinecraftVersion(boolean old) {
        // <1.17
        if (old) {
            final Set<String> oldClassMappings =
                    Set.of(
                            "net.minecraft.class_3797",
                            "net.minecraft.DetectedVersion",
                            "net.minecraft.util.MinecraftVersion");
            final Set<String> oldFieldMappings = Set.of("field_16733", "name", "field_214960_c");

            // Try classes
            for (String classMapping : oldClassMappings) {
                ClassNode node;
                try {
                    node =
                            MixinService.getService()
                                    .getBytecodeProvider()
                                    .getClassNode(classMapping);
                } catch (IOException | ClassNotFoundException e) {
                    continue;
                }

                // Try fields
                for (String fieldMapping : oldFieldMappings) {
                    String result = getAssignedConstructorStringToField(node, fieldMapping);
                    if (result != null) {
                        return result;
                    }
                }
            }

            // Failed to find a valid version
            throw new RuntimeException("Failed to locate Minecraft Version");
        } else { // >= 1.17
            final Set<String> newClassMappings =
                    Set.of(
                            "net.minecraft.class_155",
                            "net.minecraft.SharedConstants",
                            "net.minecraft.src.C_5285_");
            final Set<String> newFieldMappings =
                    Set.of("field_29733", "VERSION_STRING", "f_142952_");

            // Try classes
            for (String classMapping : newClassMappings) {
                ClassNode node;
                try {
                    node =
                            MixinService.getService()
                                    .getBytecodeProvider()
                                    .getClassNode(classMapping);
                } catch (IOException | ClassNotFoundException e) {
                    continue;
                }

                // Try fields
                for (String fieldMapping : newFieldMappings) {
                    String result = getStaticStringValue(node, fieldMapping);
                    if (result != null) {
                        return result;
                    }
                }
            }

            // Failed to find a valid version; Trying older MC versions
            return getMinecraftVersion(true);
        }
    }

    private static String getStaticStringValue(ClassNode classNode, String fieldName) {
        for (FieldNode field : classNode.fields) {
            if (field.name.equals(fieldName) && field.desc.equals("Ljava/lang/String;")) {
                return (String) field.value;
            }
        }
        return null;
    }

    private static String getAssignedConstructorStringToField(
            ClassNode classNode, String fieldName) {
        for (MethodNode method : classNode.methods) {
            if (method.name.equals("<init>")) {
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
