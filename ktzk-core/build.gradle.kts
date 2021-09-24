plugins {
    kotlin("jvm")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation(kotlin("test-junit"))
    testImplementation("io.mockk:mockk:1.12.0")
}
