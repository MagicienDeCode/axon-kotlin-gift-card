plugins {
    kotlin("jvm")
}

val VERSION = "0.0.1-SNAPSHOT"
val ARTIFACTID = "gift-card-api"

group = "fr.xiang"
version = VERSION

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // https://discuss.axoniq.io/t/getting-xstream-dependency-exception/3634/2
    implementation("org.axonframework:axon-modelling:4.5.3")
}

tasks.register("prepareKotlinBuildScriptModel") {}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>(ARTIFACTID) {
            from(components["java"])
            groupId = group.toString()
            artifactId = ARTIFACTID
            version = VERSION
        }
    }
    repositories {
        val snapshotFlag = version.toString().endsWith("SNAPSHOT")
        // change to point to your repo.
        val releasesRepoUrl = layout.buildDirectory.dir("repo")
        val snapshotsRepoUrl = layout.buildDirectory.dir("repo")
        maven {
            url = uri(
                if (snapshotFlag) snapshotsRepoUrl else releasesRepoUrl
            )
            // name = if (snapshotFlag) "libs-snapshot" else "libs-release"
            credentials {
                username = System.getenv("USERNAME")
                password = System.getenv("PASSWORD")
            }
        }
    }
}
