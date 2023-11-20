import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.serialization") version "1.8.21"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("ch.qos.logback:logback-classic:1.2.9")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.3.0")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("client.MainClientKt")
}


tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "client.MainClientKt"
        attributes["Multi-Release"] = true

    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}