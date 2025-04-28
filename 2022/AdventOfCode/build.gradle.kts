plugins {
    kotlin("jvm") version "2.1.20"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.google.guava:guava:33.4.8-jre")
    testImplementation(kotlin("test"))
}
