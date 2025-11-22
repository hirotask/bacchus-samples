plugins {
    id("buildlogic.java-application-conventions")
}

dependencies {
    implementation("com.h2database:h2:2.2.224")
}

application {
    mainClass = "com.example.java.Main"
}
