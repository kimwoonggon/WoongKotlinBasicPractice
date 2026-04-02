#!/usr/bin/env kotlin

// Normal Class
class User(val name: String, val age: Int)
var user1 = User("Alice", 29)
var user2 = User("Alice", 29)
println("일반 클래스 유저 객체 비교")
println(user1)
println(user1 == user2)
println("===============")

println("데이터 클래스")
data class DataUser(val name: String, val age: Int)
println("데이터 클래스 유저 객체 비교")
var user3 = DataUser("Alice", 30)
var user4 = DataUser("Alice", 30)

println(user3)          // 출력: DataUser(name=Alice, age=30) (toString() 자동 생성)
println(user3 == user4) // 출력: true (equals()가 내용을 비교하도록 자동 생성)

println("-------------")

// copy()로 불변 객체 다루기
val user5 = DataUser("Alice", 30)
val user6 = user5.copy(age = 31)
println(user5) // 출력: DataUser(name=Alice, age=30) (원본은 불변)
println(user6) // 출력: DataUser(name=Alice, age=30) (원본은 불변)

println()
println("구조 분해 선언")
val user7  = DataUser("Alice", 30)
val (name, age) = user7
println("이름 : $name, 나이 : $age")

println("Enum Classes")
// Enum
enum class DayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

fun getDayMessage(day: DayOfWeek): String {
    return when (day) {
        DayOfWeek.MONDAY -> "한 주의 시작! 힘내세요."
        DayOfWeek.FRIDAY -> "드디어 금요일! 주말이 다가온다."
        DayOfWeek.SATURDAY, DayOfWeek.SUNDAY -> "즐거운 주말!"
        else -> "평범한 하루." // MONDAY, FRIDAY, SATURDAY, SUNDAY를 제외한 나머지 요일
    }
}
println("getDayMessage(DayOfWeek.MONDAY)")
println(getDayMessage(DayOfWeek.MONDAY))


enum class Color(val hexCode: String) {
    RED("#FF0000"),
    GREEN("#00FF00"),
    BLUE("#0000FF"), // 프로퍼티나 메서드가 있을 경우, 상수 목록 끝에 세미콜론(;) 필수
    YELLOW("#FFFF00");

    fun isPrimaryColor(): Boolean {
        return this == RED || this == GREEN || this == BLUE
    }
}

println("YELLOW는 주 색깔인가? ${Color.YELLOW.isPrimaryColor()}")
println("GREEN는 주 색깔인가? ${Color.GREEN.isPrimaryColor()}")

println()
println("=== Sealed Class ===")
println("=== Enum보다 복잡한 상태 기술이 가능하다 ===")
sealed class Result {
    data object Loading : Result() // toString() 시 "Loading"이라고 예쁘게 찍힙니다.
    data class Success(val data: String) : Result()
    data class Error(val message: String) : Result()
}

fun handleResult(result: Result) {
    when (result) {
        is Result.Loading -> {
            println("데이터를 로딩하고 있다...")
        }
        is Result.Success -> {
            // Success 타입으로 스마트 캐스팅되어 data 프로퍼티에 안전하게 접근
            println("성공! 데이터: ${result.data}")
        }
        is Result.Error -> {
            // Error 타입으로 스마트 캐스팅되어 message 프로퍼티에 안전하게 접근
            println("실패! 메시지: ${result.message}")
        }
    } // 모든 자식 클래스를 처리했으므로 else가 필요 없다.
}

println("Sealed Class의 로딩 상태 전달")
handleResult(Result.Loading)

// 2. 성공 데이터 전달
println("Sealed class의 성공 데이터 전달")
val successData = Result.Success("서버에서 온 귀한 데이터")
handleResult(successData)

// 3. 에러 발생 전달
println("Sealed class의 에러 발생 전달")
handleResult(Result.Error("인터넷 연결이 불안정합니다."))
