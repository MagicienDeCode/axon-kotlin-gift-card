import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.5.31"
    id("org.springframework.boot") version "2.5.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    idea
    `maven-publish`
    `java-library`
}

java.sourceCompatibility = JavaVersion.VERSION_11

allprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "java-library")
    repositories {
        mavenCentral()
        /*
        maven {
            url = uri("https://???")
            credentials {
                username = System.getenv("USERNAME")
                password = System.getenv("PASSWORD")
            }
        }
         */
    }
}

tasks.names.filter { it != "clean" }.forEach {
    tasks.getByName(it) { enabled = false }
}

subprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

idea.module {
    sourceDirs.plusAssign(
        files(
            "build/generated/source/kapt/main",
            "build/generated/source/kaptKotlin/main"
        )
    )
    generatedSourceDirs.plusAssign(
        files(
            "build/generated/source/kapt/main", "build/generated/source/kaptKotlin/main"
        )
    )
}
