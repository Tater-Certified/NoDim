/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.vanilla;

import com.github.tatercertified.vanilla.annotation.MCVer;
import com.github.tatercertified.vanilla.util.MinecraftVersion;

import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.service.MixinService;

import java.io.IOException;
import java.util.*;

public class NoDimMixinPlugin implements IMixinConfigPlugin {
    private int minor;
    private int patch;

    private boolean isBetween(String min, String max) {
        int minMinor = this.getMinor(min);
        if (minor < minMinor) {
            return false;
        }

        int maxMinor = this.getMinor(max);
        if (minor > maxMinor) {
            return false;
        }

        if (minor == maxMinor || minor == minMinor) {

            int minPatch = this.getPatch(min);
            if (patch < minPatch) {
                return false;
            }

            int maxPatch = this.getPatch(max);
            return patch <= maxPatch;

        } else {
            return true;
        }
    }

    private int getMinor(String version) {
        String[] split = version.split("\\.");
        return Integer.parseInt(split[1]);
    }

    private int getPatch(String version) {
        String[] split = version.split("\\.");
        if (split.length == 3) {
            return Integer.parseInt(split[2]);
        } else {
            return 0;
        }
    }

    @Override
    public void onLoad(String s) {
        minor = this.getMinor(MinecraftVersion.getVersion());
        patch = this.getPatch(MinecraftVersion.getVersion());
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String s, String s1) {
        ClassNode mixinClassNode;
        try {
            mixinClassNode = MixinService.getService().getBytecodeProvider().getClassNode(s1);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        if (mixinClassNode.visibleAnnotations != null) {
            for (AnnotationNode annotation : mixinClassNode.visibleAnnotations) {
                if (annotation.desc.equals(MCVer.class.descriptorString())) {
                    Map<String, Object> annotationValues = new HashMap<>();

                    for (int i = 0; i < annotation.values.size(); i += 2) {
                        String k = (String) annotation.values.get(i);
                        Object v = annotation.values.get(i + 1);
                        annotationValues.put(k, v);
                    }

                    String minVer =
                            (String)
                                    annotationValues.getOrDefault(
                                            "min", MinecraftVersion.getVersion());
                    String maxVer =
                            (String)
                                    annotationValues.getOrDefault(
                                            "max", MinecraftVersion.getVersion());

                    System.out.println("Testing " + s1 + ": " + minVer + " - " + maxVer);
                    if (!this.isBetween(minVer, maxVer)) {
                        System.out.println("Disabling " + s1);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> set, Set<String> set1) {}

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(
            String s,
            org.objectweb.asm.tree.ClassNode classNode,
            String s1,
            IMixinInfo iMixinInfo) {}

    @Override
    public void postApply(
            String s,
            org.objectweb.asm.tree.ClassNode classNode,
            String s1,
            IMixinInfo iMixinInfo) {}
}
