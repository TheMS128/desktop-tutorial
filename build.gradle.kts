import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    id("java")
    id("io.qameta.allure") version "2.12.0"
    id("org.openapi.generator") version "7.14.0"
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
    testImplementation("io.qameta.allure:allure-junit5:2.29.1")
    testImplementation("io.rest-assured:rest-assured:5.5.5")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.0")

    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    implementation("org.openapitools:openapi-generator-gradle-plugin:7.14.0")
    implementation("com.google.code.gson:gson:2.13.1")
    implementation("joda-time:joda-time:2.14.0")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-joda:2.19.1")
}

tasks.test {
    useJUnitPlatform()

    doFirst {
        delete("build/reports")
        delete("build/allure-results")
    }

    finalizedBy("allureReport")
}

sourceSets {
    main {
        java {
            srcDir("$buildDir/generated-sources/swagger/src/main/java")
        }
    }
}

tasks.register<GenerateTask>("openApiGenerateBackoffice") {
    generatorName.set("java")
    remoteInputSpec.set("https://sb2admin-altenar2-stage.biahosted.com/swagger/v1/swagger.json")
    outputDir.set("$buildDir/generated-sources/swagger")
    templateDir.set("$projectDir/src/test/resources/templates")
    invokerPackage.set("")
    apiPackage.set("com.altenar.sb2.backoffice.api")
    modelPackage.set("com.altenar.sb2.backoffice.model")
    importMappings.set(
        mapOf(
            "FeedProvidersEnum" to "com.altenar.sb2.backoffice.model.FeedProvidersEnum"
        )
    )
    configOptions.set(
        mapOf(
            "dateLibrary" to "joda",
            "serializationLibrary" to "jackson",
            "interfaceOnly" to "true",
            "additionalModelTypeAnnotations" to "@lombok.Data"
        )
    )
    library = "rest-assured"
    generateApiTests = false
    generateApiDocumentation = false
    generateModelTests = false
    generateModelDocumentation = false
    globalProperties.set(
        mapOf(
            "models" to ""
        )
    )
}