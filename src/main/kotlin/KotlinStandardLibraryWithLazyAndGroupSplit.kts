#!/usr/bin/env kotlin

import kotlin.system.measureTimeMillis


val bigList = (1..1_000_000).toList()
lateinit var result: List<Int>

val listTime = measureTimeMillis {
    result = bigList
        .filter { it % 2 == 0 }
        .map { it * 2 }
        .take(5)
}

println(result)
println("result 시간: ${listTime}ms") // 32ms

println()
println("지연 연산")
println("Sequence 사용하기")

val bigList2 = (1..1_000_000).toList()
lateinit var result2: List<Int>

val sequenceTime = measureTimeMillis {
    result2 = bigList2.asSequence()
        .filter { it % 2 == 0 }
        .map { it * 2 }
        .take(5)
        .toList()
}

println(result2)
println("result2 시간: ${sequenceTime}ms") // 5ms

println()
println("무한 시퀀스")
// 1부터 시작하여 2씩 곱해지는 무한 시퀀스
val infiniteSequence = generateSequence(1) { it * 2 }

// 그중 처음 10개만 가져와서 리스트로 변환
println(infiniteSequence.take(10).toList())
// 출력: [1, 2, 4, 8, 16, 32, 64, 128, 256, 512]
println()
println("그룹화")


data class Person(val name: String, val city: String)

val people = listOf(
    Person("Alice", "서울"),
    Person("Bob", "부산"),
    Person("Charlie", "서울"),
    Person("David", "부산"),
    Person("Eve", "제주")
)

// 거주 도시(city)를 기준으로 사람들을 그룹화한다.
val peopleByCity: Map<String, List<Person>> = people.groupBy { it.city }

// pretty print the map
peopleByCity.forEach { (city, people) ->
    println("[$city]")

    people.forEach { person ->
        println(" - ${person.name}")
    }
}
println()
println("분할")
println()
println("partitioning")
val numbers = listOf(1, 2, 3, 4, 5, 6)

// 숫자를 짝수와 홀수 그룹으로 분할한다.
val (evens, odds) = numbers.partition { it % 2 == 0 }

println("짝수: $evens") // 출력: 짝수: [2, 4, 6]
println("홀수: $odds") // 출력: 홀수: [1, 3, 5]
