plugins {
    `java-library`
}

dependencies {
    // Jackson for JSON serialization/deserialization
    api("com.fasterxml.jackson.core:jackson-annotations:2.18.0")
    api("com.fasterxml.jackson.core:jackson-databind:2.18.0")

    // Nullable annotations
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
}

