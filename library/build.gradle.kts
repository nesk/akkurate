plugins {
    kotlin("multiplatform") version "1.8.21"
}

group = "me.johann"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
//    explicitApi() // TODO: enable explicit API

    jvm {
        jvmToolchain(11)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
    }
}
