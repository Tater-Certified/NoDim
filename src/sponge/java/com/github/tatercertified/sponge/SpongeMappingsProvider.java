/**
 * Copyright (c) 2025 QPCrummer
 * This project is Licensed under <a href="https://github.com/Tater-Certified/NoDim/blob/main/LICENSE">MIT</a>
 */
package com.github.tatercertified.sponge;

import com.github.tatercertified.vanilla.util.LoaderMappingsProvider;
import com.github.tatercertified.vanilla.util.Mapping;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SpongeMappingsProvider implements LoaderMappingsProvider {
    @Override
    public Mapping getMappings() {
        return Mapping.Mojmap;
    }

    @Override
    public String getMCVersion() {
        try {
            URL location = this.getClass().getProtectionDomain().getCodeSource().getLocation();

            String jarPathString = location.toURI().getSchemeSpecificPart().replace("file:///", "");
            jarPathString = jarPathString.substring(0, jarPathString.lastIndexOf('!'));

            Path jarPath = Paths.get(jarPathString);

            Path targetPath =
                    jarPath.getParent().getParent().getParent().resolve("libraries/net/minecraft");

            for (File file : targetPath.toFile().listFiles()) {
                if (!"mappings".equals(file.getName())) {
                    return file.getName();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to get Minecraft Version from Sponge");
        }
        return null;
    }
}
