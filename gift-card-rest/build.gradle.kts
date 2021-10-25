plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("kapt")
}

dependencies {
    implementation(project(":gift-card-api"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // https://discuss.axoniq.io/t/getting-xstream-dependency-exception/3634/2
    implementation("org.axonframework:axon-spring-boot-starter:4.5.3")
    implementation("org.axonframework.extensions.reactor:axon-reactor-spring-boot-starter:4.5")

    kapt("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.register("prepareKotlinBuildScriptModel") {}
