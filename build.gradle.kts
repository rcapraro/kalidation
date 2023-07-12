import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("com.adarshr.test-logger") version "3.2.0"
    id("com.jfrog.bintray") version "1.8.5"
    id("maven-publish")
    id("jacoco")
    id("org.jetbrains.dokka") version "1.6.10"
    id("se.patrikerdes.use-latest-versions") version "0.2.18"
    id("com.github.ben-manes.versions") version "0.42.0"
    signing
}

group = "io.github.rcapraro"
version = "1.9.2"

extra["isReleaseVersion"] = !version.toString().endsWith("SNAPSHOT")

repositories {
    mavenCentral()
}

val junitJupiterVersion = "5.8.2"
val arrowVersion = "1.2.0-RC"
val jakartaBeanValidationVersion = "3.0.1"
val jakartaElVersion = "4.0.0"
val glassfishElVersion = "4.0.2"
val hibernateValidatorVersion = "7.0.1.Final"
val libPhoneNumberVersion = "8.12.45"
val groovyVersion = "3.0.10"
val assertJVersion = "3.22.0"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("jakarta.validation:jakarta.validation-api:$jakartaBeanValidationVersion")
    implementation("jakarta.el:jakarta.el-api:$jakartaElVersion")
    implementation("org.glassfish:jakarta.el:$glassfishElVersion")
    implementation("org.hibernate:hibernate-validator:$hibernateValidatorVersion")
    implementation("com.googlecode.libphonenumber:libphonenumber:$libPhoneNumberVersion")
    implementation("org.apache.commons:commons-jexl3:3.2.1")
    implementation("org.codehaus.groovy:groovy-jsr223:$groovyVersion")
    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    testImplementation(platform("org.junit:junit-bom:$junitJupiterVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Wrapper> {
    gradleVersion = "7.0"
}

jacoco {
    toolVersion = "0.8.7"
}

ktlint {
    version.set("0.44.0")
}

java {
    withSourcesJar()
}

tasks.register<Jar>("dokkaJar") {
    archiveClassifier.set("javadoc")
    dependsOn("dokkaJavadoc")
    from("$buildDir/dokka/javadoc/")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/rcapraro/kalidation")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
        maven {
            name = "OSSRH"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username =
                    if (project.hasProperty("sonatype.username")) (project.property("sonatype.username") as String) else "N/A"
                password =
                    if (project.hasProperty("sonatype.password")) (project.property("sonatype.password") as String) else "N/A"
            }
        }
    }

    publications {
        create<MavenPublication>("main") {
            groupId = "io.github.rcapraro"
            artifactId = "kalidation"
            version = project.version.toString()

            from(components["java"])

            artifact(tasks["dokkaJar"])

            pom {
                name.set("Kalidation")
                description.set("JSR380 Validation DSL in Kotlin")
                url.set("https://github.com/rcapraro/kalidation")
                inceptionYear.set("2018")
                licenses {
                    license {
                        name.set("The MIT license")
                        url.set("https://opensource.org/licenses/MIT")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("rcapraro")
                        name.set("Richard Capraro")
                        email.set("richard.capraro@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/rcapraro/kalidation")
                    connection.set("scm:git:git@github.com:rcapraro/kalidation.git")
                    developerConnection.set("scm:git:git@github.com:rcapraro/kalidation.git")
                }
            }
        }
    }
}

signing {
    setRequired({
        (project.extra["isReleaseVersion"] as Boolean) && gradle.taskGraph.hasTask("publish")
    })
    useGpgCmd()
    sign(publishing.publications["main"])
}
