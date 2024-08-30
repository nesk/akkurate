import dev.nesk.akkurate.gradle.configureTargets

plugins {
    id("akkurate.kmp-library-conventions")
    id("com.google.devtools.ksp") version "2.0.20-1.0.24"
    id("org.jetbrains.dokka")
}


kotlin {
    configureTargets()

    sourceSets {
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
            }
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", project(":akkurate-ksp-plugin"))
    add("kspIosArm64", project(":akkurate-ksp-plugin"))
    add("kspIosSimulatorArm64", project(":akkurate-ksp-plugin"))
    add("kspIosX64", project(":akkurate-ksp-plugin"))
    add("kspJs", project(":akkurate-ksp-plugin"))
    add("kspJvm", project(":akkurate-ksp-plugin"))
    add("kspLinuxArm64", project(":akkurate-ksp-plugin"))
    add("kspLinuxX64", project(":akkurate-ksp-plugin"))
    add("kspMacosArm64", project(":akkurate-ksp-plugin"))
    add("kspMacosX64", project(":akkurate-ksp-plugin"))
    add("kspMingwX64", project(":akkurate-ksp-plugin"))
    add("kspTvosArm64", project(":akkurate-ksp-plugin"))
    add("kspTvosSimulatorArm64", project(":akkurate-ksp-plugin"))
    add("kspTvosX64", project(":akkurate-ksp-plugin"))
    add("kspWasmJs", project(":akkurate-ksp-plugin"))
    add("kspWatchosArm32", project(":akkurate-ksp-plugin"))
    add("kspWatchosArm64", project(":akkurate-ksp-plugin"))
    add("kspWatchosDeviceArm64", project(":akkurate-ksp-plugin"))
    add("kspWatchosSimulatorArm64", project(":akkurate-ksp-plugin"))
    add("kspWatchosX64", project(":akkurate-ksp-plugin"))
}

ksp {
    arg("__PRIVATE_API__validatablePackages", "kotlin|kotlin.collections")
    arg("__PRIVATE_API__prependPackagesWith", "dev.nesk.akkurate.accessors")
    arg("appendPackagesWith", "")
}
