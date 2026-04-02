// block이라는 이름의 파라미터로, 파라미터 없고 반환도 없는 함수(() -> Unit)를 받다.
fun measureExecutionTime(block: () -> Unit) {
    val startTime = System.nanoTime()
    block() // 전달받은 함수(코드 블록)를 여기서 실행!
    val endTime = System.nanoTime()
    val elapsedTime = (endTime - startTime) / 1_000_000.0
    println("실행 시간: ${elapsedTime}ms")
}



measureExecutionTime {
    // 측정하고 싶은 코드를 람다로 전달
    var sum = 0L
    for (i in 1..1_000_000_000) {
        sum += i
    }
}

measureExecutionTime {
    Thread.sleep(1200) // 1.2초 동안 잠시 멈추는 코드
}

println("함수를 반환하는 함수")

// 'factor'를 인자로 받아, '(Int) -> Int' 타입의 함수를 반환한다.
fun getMultiplier(factor: Int): (Int) -> Int {
    // 람다를 반환한다. 이 람다는 외부의 factor 변수를 기억한다(클로저).
    return { number -> number * factor }
}
val double: (Int) -> Int = getMultiplier(2)
val triple = getMultiplier(3)

println(double(10)) // 출력: 20
println(double(25)) // 출력: 50

println(triple(10)) // 출력: 30
println(triple(25)) // 출력: 75

println("함수 타입 사용하기")
val calculator: (Int, Int) -> Int = { a, b -> a + b } // OK
println("calculator 함수 사용 : ${calculator(10, 5)}")
println("고차 함수의 파라미터 선언")
fun processString(str: String, action: (String) -> Unit) {
    action(str)
}
processString("hello") { println(it.uppercase()) } // HELLO
println("when으로 return하기")
fun getGreetingGenerator(language: String): (String) -> String {
    return when (language) {
        "ko" -> { name -> "안녕하라, $name 님!" }
        "en" -> { name -> "Hello, $name!" }
        else -> { name -> "Greetings, $name!" }
    }
}
val koreanGreeter = getGreetingGenerator("ko")
println(koreanGreeter("홍길동")) // 안녕하라, 홍길동 님!
println()
println()
println("Null 가능 함수 타입")
var onEvent: (() -> Unit)? = null
// 이벤트가 발생하면 할당된 동작을 수행
fun doSomething() {
    println("어떤 작업을 수행한다...")
    // onEvent가 null이 아닐 경우에만 함수를 호출(invoke)한다.
    onEvent?.invoke()
}
onEvent = { println("이벤트 발생!") }
doSomething()

println("It 사용하기")
val numbers1 = listOf(1, 2, 3, 4, 5)

// 파라미터가 하나(number)인 람다
numbers1.forEach({ number -> println(number) })

val numbers2 = listOf(1, 2, 3, 4, 5)

// 후행 람다와 it을 함께 사용
numbers2.forEach { println(it) } // { number -> println(number) } 와 동일

// 각 숫자를 제곱한 새로운 리스트 만들기
val squaredNumbers = numbers2.map { it * it } // { number -> number * number } 와 동일
println(squaredNumbers) // 출력: [1, 4, 9, 16, 25]

println()
val name: String? = "Kotlin"
name?.let {
    println("이름의 길이: ${it.length}")
    println("대문자: ${it.uppercase()}")
}

