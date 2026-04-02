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