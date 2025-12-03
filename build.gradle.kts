import org.gradle.kotlin.dsl.stonecutter
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.tooling.core.linearClosureSequence
import java.net.URI

plugins {
    kotlin("jvm") version "2.2.21"
    id("fabric-loom") version "1.13-SNAPSHOT"
    id("maven-publish")
    kotlin("kapt") version "2.2.21"
}

version = project.property("mod_version") as String
group = project.property("maven_group") as String

base {
    archivesName.set(project.property("archives_base_name") as String)
}

val targetJavaVersion = if (stonecutter.eval(stonecutter.current.version, ">=1.20.5")) 21 else 17
java {
    toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}

loom {
    accessWidenerPath = file("${project.rootProject.rootDir}/src/main/resources/quicksearch.classtweaker")
}


fabricApi {
    configureDataGeneration {
        client = true
    }
}

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.

    // owo-lib
    maven("https://maven.wispforest.io")

    // Modmenu
    maven("https://maven.terraformersmc.com/") {
        name = "Terraformers"
    }
    // Create
    maven("https://maven.blamejared.com/") // JEI
    maven("https://api.modrinth.com/maven") { name = "Modrinth" } // LazyDFU for Flywheel
    maven("https://maven.createmod.net/") { // Flywheel
        name = "Create Mod Maven"
    }
    maven("https://maven.tterrag.com/") // Flywheel on both, Create and Registrate on forge
    maven("https://mvn.devos.one/releases") // Porting Lib releases
    maven("https://mvn.devos.one/snapshots") // Create and several dependencies
    maven("https://maven.jamieswhiteshirt.com/libs-release") // Reach Entity Attributes
    maven("https://raw.githubusercontent.com/Fuzss/modresources/main/maven") // Forge Config API Port

    maven { // Fabric ASM for Porting Lib
        url = URI("https://jitpack.io/")
        @Suppress("UnstableApiUsage")
        content { includeGroupAndSubgroups("com.github") }
    }

    // REI
    maven("https://maven.architectury.dev/")
    maven("https://maven.shedaniel.me")

    // JEI
    maven("https://maven.blamejared.com/")

    // ModMaven
    maven("https://modmaven.dev/") {
        name = "ModMaven"
    }
}

val mc = project.property("minecraft_version") as String

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:$mc")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${project.property("kotlin_loader_version")}")

    modImplementation("io.wispforest:owo-lib:${project.property("owo_version")}")
    // only if you plan to use owo-config
    kapt("io.wispforest:owo-lib:${project.property("owo_version")}")

    // include this if you don't want force your users to install owo
    // sentinel will warn them and give the option to download it automatically
    include("io.wispforest:owo-sentinel:${project.property("owo_version")}")

    // Fuzzywuzzy
    implementation("me.xdrop:fuzzywuzzy:${project.property("fuzzywuzzy_version")}")
    include("me.xdrop:fuzzywuzzy:${project.property("fuzzywuzzy_version")}")

    // Exp4j
    implementation("net.objecthunter:exp4j:${project.property("exp4j_version")}")
    include("net.objecthunter:exp4j:${project.property("exp4j_version")}")

    // Modmenu
    modImplementation("com.terraformersmc:modmenu:${project.property("modmenu_version")}")

    // Create
    if (properties["create_fabric_version"] != null) {
        modImplementation(
            "com.simibubi.create:create-fabric-$mc:${
                project.property(
                    "create_fabric_version"
                )
            }"
        )
        modRuntimeOnly(
            "com.simibubi.create:create-fabric-$mc:${
                project.property(
                    "create_fabric_version"
                )
            }"
        )
    }

    // REI
    modCompileOnlyApi("me.shedaniel:RoughlyEnoughItems-fabric:${project.property("rei_version")}")
//    modRuntimeOnly("me.shedaniel:RoughlyEnoughItems-fabric:${project.property("rei_version")}")
    // JEI
    // compile against the JEI API but do not include it at runtime
    modCompileOnlyApi("mezz.jei:jei-$mc-fabric:${project.property("jei_version")}")
    // at runtime, use the full JEI jar for Fabric
//    modRuntimeOnly("mezz.jei:jei-$mc-fabric:${project.property("jei_version")}")
}

stonecutter {
    constants["create"] = (properties["create_fabric_version"] != null)
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("minecraft_version", project.property("minecraft_version"))
    inputs.property("loader_version", project.property("loader_version"))
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.version,
            "minecraft_version" to project.property("minecraft_version")!!,
            "loader_version" to project.property("loader_version")!!,
            "kotlin_loader_version" to project.property("kotlin_loader_version")!!,
            "owo_version" to project.property("owo_version")!!
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    // ensure that the encoding is set to UTF-8, no matter what the system default is
    // this fixes some edge cases with special characters not displaying correctly
    // see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
    // If Javadoc is generated, this must be specified in that task too.
    options.encoding = "UTF-8"
    options.release.set(targetJavaVersion)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(targetJavaVersion.toString()))
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${project.base.archivesName.get()}" }
    }
}

// configure the maven publication
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.property("archives_base_name") as String
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}
