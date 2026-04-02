#!/usr/bin/env kotlin

// AppConfig라는 이름의 싱글턴 객체 선언
object AppConfig {
    val serverUrl = "https://api.my-app.com"
    val maxConnections = 10

    fun printConfigInfo() {
        println("Server: $serverUrl, Max Connections: $maxConnections")
    }
}
println("Connecting to ${AppConfig.serverUrl}")

AppConfig.printConfigInfo()

println()
println("동반객체 : 응집도를 위해 사용")
println()

class Circle(val radius: Double) {

    // Circle 클래스의 동반 객체
    companion object {
        const val PI = 3.14159 // 상수 선언

        fun newInstance(radius: Double): Circle {
            return Circle(radius)
        }
    }

    fun getArea(): Double {
        return radius * radius * PI // 클래스 내부에서는 동반 객체 멤버에 바로 접근 가능
    }
}

// 클래스 이름을 통해 동반 객체 멤버에 직접 접근 (자바의 static처럼)
val circle = Circle.newInstance(5.0)
println("원의 넓이: ${circle.getArea()}")
println("원주율: ${Circle.PI}")

println("팩토리 메서드 구현에 companion은 도움됨")
println()
println("팩토리 메서드")
class User private constructor(val nickname: String) { // 주 생성자를 private으로!

    companion object {
        fun newSubscribingUser(email: String): User {
            // 이메일에서 아이디 부분을 닉네임으로 사용
            val nickname = email.substringBefore('@')
            return User(nickname)
        }

        fun newGuestUser(): User {
            return User("Guest")
        }
    }
}

val subscribingUser = User.newSubscribingUser("alice@example.com")
val subscribingUser2 = User.newSubscribingUser("alice@example.com")
val guestUser = User.newGuestUser()
println("같은가 ? ${subscribingUser == subscribingUser2}")
println(subscribingUser.nickname) // 출력: alice
println(subscribingUser.nickname)
println(guestUser.nickname)       // 출력: Guest

println()
println()
println("=== annotation class ===")
println()
println()
