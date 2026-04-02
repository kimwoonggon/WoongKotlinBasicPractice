#!/usr/bin/env kotlin

// when은 switch와 비슷. 분기(branch)를 찾아 실행한다.
var dayOfWeek = 3
when (dayOfWeek) {
    1 -> println("월요일")
    2 -> println("화요일")
    3 -> println("수요일") // dayOfWeek가 3이므로 이 코드가 실행된다.
    4 -> println("목요일")
    5 -> println("금요일")
    else -> println("주말 또는 잘못된 값") // default와 같은 역할
}

dayOfWeek = 7
when (dayOfWeek) {
    1, 2, 3, 4, 5 -> println("주중 (Weekday)")
    6, 7 -> println("주말 (Weekend)") // dayOfWeek가 7이므로 여기가 실행된다.
}

var score = 85
when (score) {
    in 90..100 -> println("A 등급")
    in 80..89 -> println("B 등급") // 85는 이 범위에 속한다.
    in 70..79 -> println("C 등급")
    else -> println("F 등급")
}

println()

fun checkType(obj: Any) { // Any는 코틀린의 최상위 타입이다.
    when (obj) {
        is String -> {
            // obj가 String 타입임이 확인되고, String으로 스마트 캐스팅된다.
            println("문자열 길이: ${obj.length}")
        }
        is Int -> {
            println("정수 값의 두 배: ${obj * 2}")
        }
        else -> {
            println("알 수 없는 타입")
        }
    }
}
println("string checktype")
println(checkType("Hello"))
print("int checktype")
println(checkType(1))

println("인자 없는 when")
var name = "Kotlin"
score = 95

when {
    name == "Kotlin" && score > 90 -> {
        println("당신은 코틀린 고수군!")
    }
    name.startsWith("J") -> {
        println("혹시 Java 개발자인가?")
    }
    else -> {
        println("다양한 언어를 공부하는군!")
    }
}

// when을 expression으로
println("when을 expression으로")
score = 85
var grade = when (score) {
    in 90..100 -> "A"
    in 80..89 -> "B" // 이 분기의 값 "B"가 grade 변수에 할당된다.
    in 70..79 -> "C"
    else -> "F"
}
println("당신의 학점은 ${grade}이다.")
