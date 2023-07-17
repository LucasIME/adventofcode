plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "me.lmeireles"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:24.0-jre")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(18)
}
