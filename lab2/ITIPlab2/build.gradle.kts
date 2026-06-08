import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id("java")
    application
    id("com.gradleup.shadow") version "9.0.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven { url = uri("https://maven.aliyun.com/repository/public") }
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.20.0")

    implementation("ch.qos.logback:logback-classic:1.5.32")
    implementation("org.slf4j:slf4j-api:2.0.17")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(project(":string-utils"))
}

application {
    mainClass.set("org.example.Main")
}

tasks.withType<JavaExec> {
    systemProperty("file.encoding", "UTF-8")
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    manifest {
        attributes["Main-Class"] = "org.example.Main"
    }
    archiveFileName.set("lab2-app-all.jar")
}

abstract class PrintInfoTask : DefaultTask() {
    @TaskAction
    fun print() {
        println("=============================")
        println("Это моя первая пользовательская задача!")
        println("Проект: ${project.name}")
        println("Версия Gradle: ${project.gradle.gradleVersion}")
        println("=============================")
    }
}

tasks.register<PrintInfoTask>("printInfo") {
    group = "Custom"
    description = "Выводит информацию о проекте"
}

abstract class GenerateBuildPassportTask : DefaultTask() {
    @TaskAction
    fun generate() {
        val resourcesDir = File(project.projectDir, "src/main/resources")
        if (!resourcesDir.exists()) {
            resourcesDir.mkdirs()
        }

        val passportFile = File(resourcesDir, "build-passport.properties")

        val userName = System.getenv("USERNAME") ?: System.getenv("USER") ?: "Unknown"
        val osName = System.getProperty("os.name")
        val javaVersion = System.getProperty("java.version")
        val buildTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val message = "Сборка выполнена успешно!"

        val content = """
            build.user=$userName
            build.os=$osName
            build.java.version=$javaVersion
            build.time=$buildTime
            build.message=$message
        """.trimIndent()

        passportFile.writeText(content)
        println("Файл build-passport.properties создан в $resourcesDir")
    }
}

tasks.register<GenerateBuildPassportTask>("generateBuildPassport") {
    group = "Custom"
    description = "Генерирует файл build-passport.properties в ресурсах"
}

tasks.named("processResources") {
    dependsOn(tasks.named("generateBuildPassport"))
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}