import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val junitJupiterVersion = "5.2.0"
val arrowVersion = "0.7.2"

plugins {
    kotlin("jvm") version "1.2.51"
    java
    id("com.adarshr.test-logger") version "1.3.1"
}

group = "com.rcapraro"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("org.hibernate:hibernate-validator:6.0.10.Final")
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

tasks {
    // Use the native JUnit support of Gradle.
    "test"(Test::class) {
        useJUnitPlatform()
    }
}