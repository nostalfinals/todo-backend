plugins {
    id("java")
    id("java-library")
    id("application")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.protobuf)
}

group = "ink.nostal.todo.backend"
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly(libs.mongodb.kotlin)
    compileOnly(libs.mongodb.kotlin.bson)
    compileOnly(libs.caffeine)
    compileOnly(libs.bundles.protobuf)
    compileOnly(libs.bundles.grpc)
    compileOnly(libs.bundles.nightconfig)
    compileOnly(libs.bundles.kotlin)
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass = "ink.nostal.todo.backend.TodoKt"
}

protobuf {
    protoc {
        artifact = libs.protoc.asProvider().get().toString()
    }
    plugins {
        create("grpc") {
            artifact = libs.protoc.gen.grpc.java.get().toString()
        }
        create("grpckt") {
            artifact = libs.protoc.gen.grpc.kotlin.get().toString() + ":jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
                create("grpckt")
            }
            it.builtins {
                create("kotlin")
            }
        }
    }
}
