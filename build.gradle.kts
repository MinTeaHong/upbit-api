plugins {
//	id("org.springframework.boot") version "3.3.1"
//	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version "1.9.24"
//	kotlin("plugin.spring") version "1.9.24"
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
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	//gson
	implementation("com.google.code.gson:gson:2.8.8")
	//web소켓 설정
	implementation("org.java-websocket:Java-WebSocket:1.5.2")
	//jwt 설정
	implementation("com.auth0:java-jwt:4.3.0")
	//webclient 설정
	implementation("com.squareup.okhttp3:okhttp:4.9.2")
	implementation("org.springframework.boot:spring-boot-starter-webflux:2.7.4")
	implementation("io.netty:netty-resolver-dns-native-macos:4.1.75.Final:osx-aarch_64")
	//test 설정
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.slf4j:slf4j-api:1.7.30")
	testImplementation("org.slf4j:slf4j-simple:1.7.30")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
