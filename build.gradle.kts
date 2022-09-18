import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
val miraiVersion = "2.12.2"
plugins {
    id("org.springframework.boot") version "3.0.0-SNAPSHOT"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.21"
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.spring") version "1.7.10"
}

group = "io.github.R29kTA"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    api(platform("net.mamoe:mirai-bom:$miraiVersion"))
    api("net.mamoe:mirai-core-api")     // 编译代码使用
    runtimeOnly("net.mamoe:mirai-core") // 运行时使用
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
