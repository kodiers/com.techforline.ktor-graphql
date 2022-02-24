val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
}

group = "com.techforline"
version = "0.0.1"
application {
    mainClass.set("com.techforline.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.insert-koin:koin-ktor:3.1.5")
    implementation("org.litote.kmongo:kmongo:4.5.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.apurebase:kgraphql:0.17.14")
    implementation("com.apurebase:kgraphql-ktor:0.17.14")
    implementation("io.ktor:ktor-auth:1.6.7")
    implementation("io.ktor:ktor-auth-jwt:1.6.7")
    implementation("at.favre.lib:bcrypt:0.9.0")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}