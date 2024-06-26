plugins {
	id("org.springframework.boot") version "3.3.1"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
}

group = "com.salgam"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	implementation("org.java-websocket:Java-WebSocket:1.5.2")
	implementation("com.google.code.gson:gson:2.8.8")
	// https://mvnrepository.com/artifact/org.apache.directory.studio/org.apache.commons.codec
	implementation("org.apache.directory.studio:org.apache.commons.codec:1.8")

	// https://mvnrepository.com/artifact/com.auth0/java-jwt
	implementation("com.auth0:java-jwt:4.3.0")
	implementation("com.squareup.okhttp3:okhttp:4.9.2")
	implementation("org.springframework.boot:spring-boot-starter-logging")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")




	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
