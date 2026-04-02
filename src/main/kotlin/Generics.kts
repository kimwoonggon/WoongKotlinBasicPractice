#!/usr/bin/env kotlin
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.CharArrayWriter
// 제너릭
// 인자에 val을 붙인다는건 property 선언이다.
// intBox.item 과 같이 getter처럼 사용할 수 있다.
class Box<T>(val item: T) {
    fun fetchItem() : T {
        return item
    }
}

println("int Generic 선언")
val intBox = Box<Int>(100)
val intValue: Int = intBox.fetchItem() // 꺼낼 때 타입 변환이 필요 없다.
println(intValue) // 출력: 100
println()
println("string generic 선언")
val stringBox = Box("Hello Generic")
val stringValue: String = stringBox.fetchItem()
println(stringValue)

println()
println("제너릭 인터페이스")

interface Serializer<T> {
    fun serialize(item: T): String
    fun deserialize(source: String): T
}

class Person(val name: String, val age: Int)


class PersonSerializer : Serializer<Person> {
    override fun serialize(item: Person): String {
        // Person 객체를 JSON 문자열 등으로 변환하는 로직
        return "{ 'name': '${item.name}' }"
    }

    override fun deserialize(source: String): Person {
        // JSON 문자열을 Person 객체로 변환하는 로직
        return Person("Deserialized Name", 0) // 간단한 예시
    }
}

val personSerializer = PersonSerializer()
val person = Person("Dmitry", 18)
println("person Serializer and deserializer")
print(personSerializer.serialize(person))
println()
print(personSerializer.deserialize("안녕"))

// 제네릭 함수
println()
fun <T> printItem(item: T) {
    println("Item is: $item")
}
printItem(42)
printItem("Kotlin")
printItem(Person("Alice", 30))

// 제너릭 Upper Bound 설정
fun <T : Comparable<T>> findMax(a: T, b: T): T {
    // 이제 컴파일러는 T가 비교 가능한(comparable) 타입임을 알기 때문에
    // > 연산자를 안전하게 사용할 수 있다.
    return if (a > b) a else b
}
println(findMax(10, 20))      // 출력: 20 (Int는 Comparable<Int>를 구현)
println(findMax("Kotlin", "Java")) // 출력: Kotlin (String은 Comparable<String>를 구현)

// 타입 파라미터 제약애서 where 설정
// T는 Appendable과 Closeable을 모두 구현해야 한다.
// T는 C#에서 인터페이스라고 생각하면 편하다.
fun <T> useAndClose(item: T) where T : Appendable, T : java.io.Closeable {
    try {
        item.append("Hello, World!")
        println(item)
    } finally {
        item.close()
    }
}
val writer = BufferedWriter(FileWriter("test.txt"))
useAndClose(writer)
val fileWriter = FileWriter("hello.txt")
useAndClose(fileWriter)
val charWriter = CharArrayWriter()
useAndClose(charWriter)
println("결과: ${charWriter.toString()}")

// 스타 프로젝션
println("스타 프로젝션 (!! 쓰기 불가능 !!)")
fun printAnyList(list: MutableList<*>) {
    // 1. 읽기(out): 가능. 각 item은 Any? 타입으로 취급됨
    for (item in list) {
        println(item)
    }

    // 2. 쓰기(in): 불가능. 어떤 타입을 넣어야 할지 알 수 없으므로 컴파일 오류 발생
    // list.add(10) // 컴파일 오류!

    // size와 같이 타입 T와 무관한 메서드는 호출 가능
    println("리스트 크기: ${list.size}")
}
val strings: MutableList<String> = mutableListOf("A", "B", "C")
val ints: MutableList<Int> = mutableListOf(1, 2, 3)
println("스타 프로젝션 출력")
printAnyList(strings) // OK!
printAnyList(ints)    // OK!
println("스타 프로젝션 끝")

// Refied
// 특정 type에 맞는 것 찾기
println("refied 시작")
println("type에 맞는 것 중 가장 앞에 있는것 출력")
inline fun <reified T> List<Any>.findFirstInstanceOf(): T? {
    for (item in this) {
        if (item is T) {
            return item // item이 T 타입이면 스마트 캐스팅되어 안전하게 반환
        }
    }
    return null
}

val mixedList: List<Any> = listOf("Kotlin", 1, "Java", 2.0, Person("Alice", 30))
// String 타입의 첫 번째 요소를 찾다.
val firstString = mixedList.findFirstInstanceOf<String>()
println(firstString) // 출력: Kotlin

// Person 타입의 첫 번째 요소를 찾다.
val firstPerson = mixedList.findFirstInstanceOf<Person>()
println("firstPerson이 Null일 수도 있으니 조심")
println("${firstPerson}, ${firstPerson?.name}, ${firstPerson?.age}") // 출력: Person(name=Alice, birthYear=30)