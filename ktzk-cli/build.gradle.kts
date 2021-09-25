plugins {
    id ("org.jetbrains.kotlin.jvm")
    id ("application")
    id ("java")
    id ("com.github.johnrengelman.shadow") version "6.1.0"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation(project(":ktzk-core"))

    implementation("com.github.ajalt.clikt:clikt:3.2.0")
}

application {
    mainClassName = "MainKt"
}
