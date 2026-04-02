#!/usr/bin/env kotlin
println("Immutable List")
val numbers: List<Int> = listOf(1, 2, 3, 4, 3) // 중복 허용
println(numbers[0])      // 1
println(numbers.size)    // 5
// numbers.add(5) // 컴파일 오류! 읽기 전용 리스트는 수정할 수 없다.
println()
println("MutableNumbers")
val mutableNumbers: MutableList<Int> = mutableListOf(1, 2, 3)
println(mutableNumbers) // [1, 2, 3]

mutableNumbers.add(4)      // 맨 끝에 4 추가
mutableNumbers.removeAt(0) // 0번 인덱스 요소(1) 제거
mutableNumbers[1] = 100    // 1번 인덱스 요소(3)를 100으로 변경

println(mutableNumbers) // [2, 100, 4]

println("읽기 전용 Set")
val fruits: Set<String> = setOf("사과", "바나나", "오렌지", "바나나")
println(fruits) // [사과, 바나나, 오렌지] (순서는 다를 수 있음)
println("mutable Set")
val mutableFruits: MutableSet<String> = mutableSetOf("사과")
mutableFruits.add("바나나") // true 반환 (추가 성공)
mutableFruits.add("사과")   // false 반환 (이미 존재하므로 추가 실패)
println(mutableFruits) // [사과, 바나나]


println("읽기 전용 Map")
val userAges: Map<String, Int> = mapOf(
    "Alice" to 30,
    "Bob" to 25
)
println(userAges["Alice"]) // 30
println()
println("변경 가능 Map")
val mutableUserAges: MutableMap<String, Int> = mutableMapOf("Alice" to 30)
mutableUserAges["Bob"] = 25      // 새로운 쌍 추가
mutableUserAges["Alice"] = 31    // "Alice" 키의 값 수정
mutableUserAges.remove("Bob")    // "Bob" 키의 쌍 제거
println(mutableUserAges) // {Alice=31}
println("변경 가능 Map 끝")
println()

println("동적 컬렉션 생성")
val evenNumbers: List<Int> = buildList {
    for (i in 0 until 10)
    {
        if (i % 2 == 0)
        {
            add(i)
        }
    }
}
println(evenNumbers)

println()
println("다른 컬렉션으로 복사하기")
val originalList = listOf(1, 2, 3, 2)

// List를 Set으로 변환 (중복이 제거됨)
val numberSet = originalList.toSet()
println(numberSet) // 출력: [1, 2, 3]

// Set을 변경 가능한 List로 변환
val mutableListFromSet = numberSet.toMutableList()
mutableListFromSet.add(4)
println(mutableListFromSet) // 출력: [1, 2, 3, 4]

println()
println("reduce 살펴보기")
val numbers1 = listOf(1, 2, 3, 4, 5) // 합계: 15
val sumByReduce = numbers1.reduce { accumulator, current ->
    println("$accumulator + $current")
    accumulator + current
}
println(sumByReduce)

println("fold")
println("fold는 reduce랑 다르게 초기값 제공")
val numbers2 = listOf(1, 2, 3, 4, 5)

// 초기값을 100으로 시작하여 모든 요소를 더함
val sumByFold = numbers2.fold(100) { accumulator, current ->
    println("$accumulator + $current")
    accumulator + current
}
println(sumByFold) // 출력: 115

println()
println("*** foreach ***")
println()

val fruits2 = listOf("사과", "바나나", "딸기")

// for 루프 사용
for (fruit in fruits2) {
    println("${fruit}!")
}

// forEach 사용
fruits2.forEach { fruit -> println("${fruit}!") }

// 'it'을 사용하면 더 간결해진다.
fruits2.forEach { println("${it}!") }

println()
println("*** map ***")
println()
val numbers3 = listOf(1, 2, 3, 4, 5)

// 각 숫자를 제곱한 새로운 리스트 만들기
val squaredNumbers = numbers3.map { it * it }
println(squaredNumbers) // 출력: [1, 4, 9, 16, 25]

// 각 과일의 이름 길이를 담은 리스트 만들기
val nameLengths = fruits2.map { it.length }
println(nameLengths) // 출력: [2, 3, 2]