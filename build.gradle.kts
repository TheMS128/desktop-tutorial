plugins {
    id("java")
    id("io.qameta.allure") version "2.12.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.13.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.hamcrest:hamcrest:3.0")
    implementation("org.seleniumhq.selenium:selenium-java:4.33.0")
    testImplementation("io.qameta.allure:allure-junit5:2.29.1")
}

tasks.test {
    useJUnitPlatform()

    doFirst {
        delete("build/reports")
        delete("build/allure-results")
    }

    finalizedBy("allureReport")
}
