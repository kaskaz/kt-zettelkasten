import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    kotlin("jvm") version "1.5.31"
}

allprojects {
    group = "com.github.kaskaz"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
