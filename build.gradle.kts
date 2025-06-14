import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.wagyourtail.unimined.api.minecraft.task.RemapJarTask
import java.time.Instant

plugins {
    id("java")
    id("maven-publish")
    id("idea")
    id("eclipse")
    alias(libs.plugins.shadow)
    alias(libs.plugins.spotless)
    alias(libs.plugins.unimined)
}

base {
    archivesName = "$modName-$minecraftVersion"
}

java.toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
java.sourceCompatibility = JavaVersion.toVersion(javaVersion)
java.targetCompatibility = JavaVersion.toVersion(javaVersion)

spotless {
    format("misc") {
        target("*.gradle.kts", ".gitattributes", ".gitignore")
        trimTrailingWhitespace()
        leadingTabsToSpaces()
        endWithNewline()
    }
    java {
        target("src/**/*.java", "src/**/*.java.peb")
        toggleOffOn()
        importOrder()
        removeUnusedImports()
        cleanthat()
        googleJavaFormat("1.24.0")
            .aosp()
            .formatJavadoc(true)
            .reorderImports(true)
        formatAnnotations()
        trimTrailingWhitespace()
        leadingTabsToSpaces()
        endWithNewline()
        licenseHeader("""/**
 * Copyright (c) 2025 $author
 * This project is Licensed under <a href="$sourceUrl/blob/main/LICENSE">$license</a>
 */""")
    }
}

val fabric: SourceSet by sourceSets.creating
val forge: SourceSet by sourceSets.creating
val neoforge: SourceSet by sourceSets.creating
val paper: SourceSet by sourceSets.creating
val sponge: SourceSet by sourceSets.creating

val mainCompileOnly: Configuration by configurations.creating
configurations.compileOnly.get().extendsFrom(mainCompileOnly)
val mainAnnotationProcessor: Configuration by configurations.creating
configurations.annotationProcessor.get().extendsFrom(mainAnnotationProcessor)
val fabricCompileOnly: Configuration by configurations.getting
val forgeCompileOnly: Configuration by configurations.getting
val neoforgeCompileOnly: Configuration by configurations.getting
val paperCompileOnly: Configuration by configurations.getting {
    extendsFrom(mainCompileOnly)
}
val spongeCompileOnly: Configuration by configurations.getting {
    extendsFrom(mainCompileOnly)
}
val modImplementation: Configuration by configurations.creating
val fabricModImplementation: Configuration by configurations.creating {
    extendsFrom(modImplementation)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<RemapJarTask> {
    mixinRemap {
        enableBaseMixin()
        disableRefmap()
    }
}

repositories {
    // maven("https://maven.neuralnexus.dev/mirror")
    mavenCentral()
    unimined.fabricMaven()
    unimined.minecraftForgeMaven()
    unimined.neoForgedMaven()
    unimined.parchmentMaven()
    unimined.spongeMaven()
    maven("https://repo.papermc.io/repository/maven-public/")
}

unimined.minecraft {
    version(minecraftVersion)
    mappings {
        parchment(parchmentMinecraft, parchmentVersion)
        mojmap()
        devFallbackNamespace("official")
    }
    defaultRemapJar = false
}

unimined.minecraft(fabric) {
    combineWith(sourceSets.main.get())
    fabric {
        loader(fabricLoaderVersion)
    }
    defaultRemapJar = true
}

tasks.register<ShadowJar>("relocateFabricJar") {
    dependsOn("remapFabricJar")
    from(tasks.getByName<RemapJarTask>("remapFabricJar").asJar.archiveFile.get().asFile)
    archiveClassifier.set("fabric-relocated")
    dependencies {
        exclude("com/example/templatemod/mixin/vanilla/**")
    }
    relocate("com.example.templatemod.vanilla", "com.example.templatemod.y_intmdry")
}

unimined.minecraft(forge) {
    combineWith(sourceSets.main.get())
    minecraftForge {
        loader(forgeVersion)
    }
    defaultRemapJar = true
}

unimined.minecraft(neoforge) {
    combineWith(sourceSets.main.get())
    neoForge {
        loader(neoForgeVersion)
    }
    defaultRemapJar = true
}

unimined.minecraft(paper) {
    combineWith(sourceSets.main.get())
    accessTransformer {
        // https://github.com/PaperMC/Paper/blob/main/build-data/paper.at
        accessTransformer("${rootProject.projectDir}/src/paper/paper.at")
    }
    defaultRemapJar = true
}

unimined.minecraft(sponge) {
    combineWith(sourceSets.main.get())
    defaultRemapJar = true
}

dependencies {
    mainCompileOnly(libs.annotations)
    mainCompileOnly(libs.mixin)
    paperCompileOnly("io.papermc.paper:paper-api:$minecraftVersion-$paperVersion")
    paperCompileOnly(libs.ignite.api)
    spongeCompileOnly("org.spongepowered:spongeapi:$spongeVersion")
}

tasks.withType<ProcessResources> {
    filesMatching(listOf(
        "fabric.mod.json",
        "pack.mcmeta",
        "META-INF/mods.toml",
        "META-INF/neoforge.mods.toml",
        "plugin.yml",
        "paper-plugin.yml",
        "ignite.mod.json",
        "META-INF/sponge_plugins.json",
    )) {
        expand(project.properties)
    }
}

tasks.jar {
    from(
        fabric.output,
        forge.output,
        neoforge.output,
        paper.output,
        sponge.output,
    )
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes(
            mapOf(
                "Specification-Title" to modName,
                "Specification-Version" to version,
                "Specification-Vendor" to "SomeVendor",
                "Implementation-Version" to version,
                "Implementation-Vendor" to "SomeVendor",
                "Implementation-Timestamp" to Instant.now().toString(),
                "FMLCorePluginContainsFMLMod" to "true",
                "TweakClass" to "org.spongepowered.asm.launch.MixinTweaker",
                "MixinConfigs" to "$modId.mixins.vanilla.json"
            )
        )
    }
    from(listOf("README.md", "LICENSE")) {
        into("META-INF")
    }
}
tasks.build.get().dependsOn("spotlessApply")
