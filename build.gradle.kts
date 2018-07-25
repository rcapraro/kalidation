import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val junitJupiterVersion = "5.2.0"
val arrowVersion = "0.7.2"

plugins {
    kotlin("jvm") version "1.2.51"
    java
    id("com.adarshr.test-logger") version "1.3.1"
    jacoco
}

group = "com.rcapraro"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    // This repository is needed to get the latest snapshot of JaCoCo
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("org.hibernate:hibernate-validator:6.0.11.Final")
    compile("com.googlecode.libphonenumber:libphonenumber:8.9.10")
    compile("org.glassfish:javax.el:3.0.1-b10")
    compile("io.arrow-kt:arrow-core:$arrowVersion")
    compile("io.arrow-kt:arrow-data:$arrowVersion")
    testCompile("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testRuntime("org.junit.platform:junit-platform-launcher:1.2.0")
    testCompile("org.assertj:assertj-core:3.10.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

jacoco {
    toolVersion = "0.8.2-SNAPSHOT"
}

tasks {
    // Use the native JUnit support of Gradle.
    "test"(Test::class) {
        useJUnitPlatform()
    }
}