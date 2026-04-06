#!/usr/bin/env kotlin

@file:Repository("https://repo1.maven.org/maven2/")
@file:DependsOn("io.ktor:ktor-server-core-jvm:2.3.12")
@file:DependsOn("io.ktor:ktor-server-netty-jvm:2.3.12")
@file:DependsOn("io.ktor:ktor-server-content-negotiation-jvm:2.3.12")
@file:DependsOn("io.ktor:ktor-serialization-gson-jvm:2.3.12")

import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

// User 데이터 클래스 (Gson은 @Serializable 없이 동작)
data class User(val id: Int, val name: String)

// 간단한 인메모리 데이터베이스
val userDatabase = mutableListOf(User(1, "Alice"), User(2, "Bob"))

println("서버 시작: http://localhost:8999")

embeddedServer(Netty, port = 8999) {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
        route("/users") {
            // GET /users : 모든 사용자 목록을 JSON 배열로 반환
            get {
                call.respond(userDatabase)
            }

            // POST /users : 새 사용자를 JSON으로 받아 등록
            post {
                val newUser = call.receive<User>()
                userDatabase.add(newUser)
                call.respond(HttpStatusCode.Created, newUser)
            }

            // GET /users/{id} : 특정 사용자 반환
            get("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                val user = userDatabase.find { it.id == id }

                if (user != null) {
                    call.respond(user)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "User not found"))
                }
            }
        }
    }
}.start(wait = true)
