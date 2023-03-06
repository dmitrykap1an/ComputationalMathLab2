plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://repo.kotlin.link")
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation ("org.jetbrains.lets-plot:lets-plot-batik:3.0.0")
    implementation ("org.jetbrains.lets-plot:lets-plot-kotlin-jvm:4.2.0")
   // api("space.kscience:kmath-core:0.3.1-dev-1")



}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}