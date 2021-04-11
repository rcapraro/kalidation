import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
    id("com.adarshr.test-logger") version "3.0.0"
    id("com.jfrog.bintray") version "1.8.5"
    id("maven-publish")
    id("jacoco")
    id("org.jetbrains.dokka") version "1.4.30"
    id("se.patrikerdes.use-latest-versions") version "0.2.15"
    id("com.github.ben-manes.versions") version "0.38.0"
}

group = "com.capraro"
version = "1.6.0"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

val junitJupiterVersion = "5.7.1"
val arrowVersion = "0.11.0"
val hibernateValidatorVersion = "6.2.0.Final"
val javaxValidationVersion = "2.0.1.Final"
val libphonenumberVersion = "8.12.21"
val groovyVersion = "3.0.7"
val assertJVersiom = "3.19.0"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("javax.validation:validation-api:$javaxValidationVersion")
    implementation("org.hibernate:hibernate-validator:$hibernateValidatorVersion")
    implementation("com.googlecode.libphonenumber:libphonenumber:$libphonenumberVersion")
    implementation("org.glassfish:javax.el:3.0.1-b12")
    implementation("org.apache.commons:commons-jexl3:3.1")
    implementation("org.codehaus.groovy:groovy-jsr223:$groovyVersion")
    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    implementation("io.arrow-kt:arrow-validation:$arrowVersion")
    testImplementation(platform("org.junit:junit-bom:$junitJupiterVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testCompile("org.assertj:assertj-core:$assertJVersiom")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Wrapper> {
    gradleVersion = "7.0"
}

jacoco {
    toolVersion = "0.8.6"
}

ktlint {
    version.set("0.41.0")
}
