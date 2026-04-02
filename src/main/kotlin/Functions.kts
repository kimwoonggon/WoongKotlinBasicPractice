#!/usr/bin/env kotlin

fun createUser(
    name: String,
    age: Int,
    country: String = "Korea", // country의 기본값은 "Korea"
    language: String = "Korean", // language의 기본값은 "Korean"
    isActive: Boolean = true // isActive의 기본값은 true
) {
    println("이름: $name, 나이: $age, 국가: $country, 언어: $language, 활성: $isActive")
}

createUser("Alice", 30)

// 출력: 이름: Alice, 나이: 30, 국가: Korea, 언어: Korean, 활성: true
createUser("Bob", 25, "USA") // country만 "USA"로 변경, 나머지는 기본값
// 출력: 이름: Bob, 나이: 25, 국가: USA, 언어: Korean, 활성: true


println("이름이 명시되면 순서가 바뀌어도 상관없다.")
createUser(name = "Charlie", age = 40, isActive = false)
println("순서를 바꿔서 호출해도 전혀 문제없다!")
createUser(age = 22, isActive = false, name = "David")
println("올바른 사용: 일반 인자(name, age) 뒤에 이름 있는 인자(language)")

createUser("Eve", 28, language = "English")

// 가변인자
fun printNumbers(vararg numbers: Int) {
    println("전달받은 숫자의 개수: ${numbers.size}")
    for (number in numbers) {
        print("$number ")
    }
    println()
}
println("가변인자 테스트")
printNumbers(1, 2, 3)
printNumbers(10, 20, 30, 40, 50)
printNumbers()

// varargs는 함수에 하나의 vararg를 허용함
println()
println("intarray에는 *를 붙인다")
val numbersArray = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
printNumbers(*numbersArray)

//
//최상위함수
//

println("최상위함수")
println("is all uppercase 구현")
// it은 iterator로 character 하나씩 돈다
fun isAllUpperCase(str: String): Boolean {
    return str.all { it.isUpperCase() }
}

val text1 = "HELLO"
val text2 = "Hello"
println("$text1 is all upper case? ${isAllUpperCase(text1)}") // true
println("$text2 is all upper case? ${isAllUpperCase(text2)}") // false
println()
println()
println("지역함수")
fun saveUser(name: String, email: String, address: String) {

    // saveUser 함수 안에서만 사용될 지역 함수
    fun validate(value: String, fieldName: String) {
        if (value.isBlank()) {
            // isBlank()는 공백 문자로만 이루어진 경우도 true를 반환
            throw IllegalArgumentException("'$fieldName' cannot be blank.")
        }
    }

    validate(name, "Name")
    validate(email, "Email")
    validate(address, "Address")

    // 모든 유효성 검사를 통과하면 사용자 정보를 저장하는 로직...
    println("Saving user: $name, $email, $address")
}
//saveUser("Alice", "Alice", "")
saveUser("Alice", "Alice", "Seoul")
// java.lang.IllegalArgumentException: 'Address' cannot be blank.

// 확장함수
// 상속은 원래 class가 final이면 불가능
println("확장함수 정의")
fun String.lastChar(): Char {
    return this[this.length - 1]
}
var message = "Hello 웅곤"
var lastChar = message.lastChar();
println("'$message' last char is '$lastChar'.")

println()
println("확장함수 다른 예시")
fun Int.isEven(): Boolean {
    return this % 2 == 0
}
var number = 10
if (number.isEven()) { // Int 타입 값에서 바로 isEven() 호출
    println("$number is even.") // 10 is even.
}


// 중위함수
println("*** 중위함수 ***")

// 중위함수의 조건
//반드시 멤버 함수 또는 확장 함수여야 한다.
////반드시 단 하나의 파라미터만 가져야 한다.
////파라미터는 vararg이거나 기본값을 가져서는 안 된다.
////함수 선언 앞에 infix 키워드를 붙여야 한다.

// String 클래스에 대한 확장 함수로, infix 키워드를 붙이다.
infix fun String.startsWith(prefix: String): Boolean {
    return this.startsWith(prefix)
}

message = "Hello Kotlin"

// 중위 표기법으로 함수 호출
val result1 = message startsWith "Hello"
println(result1) // 출력: true

// 기존의 표준적인 호출 방식도 여전히 가능하다.
val result2 = message.startsWith("Kotlin")
println(result2) // 출력: false

//to: 가장 대표적인 중위 함수이다. 두 값을 Pair 객체로 묶어주는 역할을 한다. Map을 초기화할 때 매우 유용하다.
//
//kotlin val capitals = mapOf( "대한민국" to "서울", // "대한민국".to("서울") 과 동일 "일본" to "도쿄", "중국" to "베이징" )
//
//    범위(Range) 관련 함수: 3장에서 배운 범위 관련 함수들도 중위 함수이다.
//
//kotlin for (i in 10 downTo 1) { ... } // 10.downTo(1) for (i in 1..10 step 2) { ... } // (1..10).step(2)
//
//비트 연산 함수: and, or, xor 등 비트 연산을 위한 함수들도 중위 함수로 제공되어 연산자처럼 사용할 수 있다.
//
//kotlin val result = 10 and 12 // 10.and(12)