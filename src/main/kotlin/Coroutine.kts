#!/usr/bin/env kotlin
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.swing.Swing
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.channels.Channel

println("suspend")

// suspend 키워드를 붙여 일시 중단 함수로 선언
suspend fun fetchDataFromServer(): String {
    println("데이터 다운로드 시작...")
    // delay는 코루틴 라이브러리에서 제공하는 특별한 suspend 함수이다.
    // 지정된 시간(밀리초)만큼 코루틴을 '차단하지 않고' 기다린다.
    delay(1000L) // 1초 동안 대기하는 중단점!
    println("데이터 다운로드 완료!")
    return "서버 데이터"
}
// 일반 함수는 코루틴을 부를 수 없다.
suspend fun doSomethingSuspending() {
    delay(100)
}
// globalscope는 지양한다. 왠만하면. (scope이 추적되지 않음)
GlobalScope.launch { // launch는 결과 반환 안해도 됨
    println("코루틴 시작!")
    delay(1000L) // 1초간 코루틴 일시 중단
    println("World!")
}


println("Hello,") // launch 블록 밖의 코드는 바로 실행됨
Thread.sleep(2000L) // 메인 스레드가 2초간 대기 (코루틴이 끝날 시간을 벌어줌)
println("메인 스레드 종료")

println("async 사용하기")

suspend fun fetchUser(id: String): String {
    delay(1000L)
    return "User($id)"
}
// runblocking이란 무엇인가
runBlocking { // runblocking은 UI에서 사용하지 않도록 한다.
    val deferredUser: Deferred<String> = GlobalScope.async { // async가 붙어야 결과 반환
        fetchUser("user123")
    }
    println("사용자 정보를 기다리는 동안 다른 작업을 한다...")
    val user: String = deferredUser.await()
    // runblocking에서는 user print가 끝나야 함
    println(user)
}

println("코루틴 Job 던지기")
runBlocking {
    val job: Job = launch {
        try {
            repeat(1000) { i ->
                println("Job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            // 취소될 때 항상 실행되는 정리 코드
            println("Job: I'm running in finally!")
        }
    }
    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancel() // 코루틴을 취소한다.
    job.join()   // 코루틴이 완전히 취소될 때까지 기다린다.
    println("main: Now I can quit.")
}


println()
println()
println("Deferred Test")
println()
println("Deferred는 Job의 모든 기능을 가지면서 코루틴의 실행 값을 받을 수 있다")
suspend fun calculateResult(): Int {
    delay(1000L)
    return 42
}
runBlocking {
    val deferred: Deferred<Int> = async {
        calculateResult()
    }

    println("계산이 진행되는 동안 다른 작업을 할 수 있다.")

    // await()를 호출하여 결과가 준비될 때까지 기다린다.
    val result = deferred.await()
    println("계산 결과: $result")
}
// 코루틴의 스레드 배정
// Dispatchers.Main : UI 프리징 조심, Main Looper 스레드에만 할당한다.
// Dispatchers.Default : 코어수 가능한한 최대한 활용
// Dispatchers.IO : 스레드를 많이 할당 (스레드풀)



println()
println("context switching example")
// 안드로이드 ViewModel에서 실행한다고 가정
//fun fetchAndShowUser() {
//    viewModelScope.launch { // 기본적으로 Dispatchers.Main에서 시작
//        // 1. Main 스레드: 로딩 UI 표시
//        showLoadingIndicator()
//
//        // 2. IO 스레드로 전환하여 네트워크 작업 수행
//        val user = withContext(Dispatchers.IO) {
//            apiClient.fetchUserData()
//        }
//
//        // 3. withContext 블록이 끝나면 자동으로 Main 스레드로 복귀!
//        //    결과를 UI에 표시
//        hideLoadingIndicator()
//        myTextView.text = user.name
//    }
//}
suspend fun fetchUserData(): String {
    delay(1000) // 네트워크 지연 시뮬레이션
    return "User_Data_From_Server"
}

fun calculateHeavySum(): Long {
    var sum = 0L
    for (i in 1..1_000_000_000L) sum += i // CPU 집약적 작업
    return sum
}

// 2. 메인 실행부
runBlocking {
    println("🚀 스크립트 시작 (Thread: ${Thread.currentThread().name})\n")

    // --- Dispatchers.IO: 네트워크/DB 작업 ---
    val ioJob = async(Dispatchers.IO) {
        println("📡 [IO] 데이터 가져오는 중... (Thread: ${Thread.currentThread().name})")
        val data = fetchUserData()
        "결과: $data"
    }

    // --- Dispatchers.Default: CPU 연산 작업 ---
    val defaultJob = async(Dispatchers.Default) {
        println("💻 [Default] 복잡한 계산 중... (Thread: ${Thread.currentThread().name})")
        val time = measureTimeMillis { calculateHeavySum() }
        "계산 완료 (소요시간: ${time}ms)"
    }

    // 작업 결과 기다리기
    println("⏳ 작업 완료 대기 중...")
    val results = awaitAll(ioJob, defaultJob)

    // --- Dispatchers.Main (또는 Main 스레드): 결과 출력(UI 업데이트 역할) ---
    // 주의: 스크립트 환경엔 Main Dispatcher가 없을 수 있으므로
    // 여기서는 기본 Main 스레드에서 출력을 수행합니다.
    withContext(Dispatchers.Main) {
        println("🎨 [Main] UI 업데이트 완료! (Thread: ${Thread.currentThread().name})")
    }
    println("\n✅ 모든 작업 완료!")
    results.forEach { println("📝 $it") }

    println("\n🏁 스크립트 종료 (Thread: ${Thread.currentThread().name})")
}

println("올바른 스코프 사용법")

class MyComponent {
    // 이 컴포넌트의 생명주기에 묶인 CoroutineScope를 생성한다.
    // Job()을 통해 이 스코프 내의 모든 코루틴을 한 번에 제어할 수 있다.
    private val scope = CoroutineScope(Dispatchers.Default + Job())

    fun performTask() {
        // 컴포넌트의 스코프에서 코루틴을 시작한다.
        scope.launch {
            println("MyComponent의 작업 시작...")
            delay(2000L)
            println("MyComponent의 작업 완료!") // 이 메시지는 출력되지 않을 것이다.
        }
    }

    // 컴포넌트가 파괴될 때 호출되는 메서드
    fun destroy() {
        println("컴포넌트가 파괴된다. 모든 작업을 취소한다.")
        // 스코프를 취소하면, 이 스코프에서 시작된 모든 자식 코루틴들이 함께 취소된다.
        scope.cancel()
    }
}

runBlocking {
    val component = MyComponent()
    component.performTask()
    delay(1000L) // 작업이 1초간 실행되도록 잠시 대기
    component.destroy()  // 1초 후 컴포넌트 파괴 (스코프 취소)
    delay(2000L) // 프로그램이 바로 끝나지 않도록 잠시 대기
}

//아래의 두개의 launch는 동시에 실행되나 하나의 job이 크래시나면 부모까지 crash 되어버림
println()
println("두 개의 launch")
println()
runBlocking {
    val scope1 = CoroutineScope(Dispatchers.Default + Job())
    val job = scope1.launch { // 부모 코루틴 (job)
        launch { // 자식 1
            println("자식 1: 시작")
            delay(1000L)
            println("자식 1: 종료") // 이 코드는 실행되지 않다.
        }
        launch { // 자식 2
            try {
                println("자식 2: 시작")
                delay(500L)
                throw IllegalStateException("자식 2에서 에러 발생!")
            } catch (e: IllegalStateException) {
                println("예외 발생: ${e.message}. 복구 로직을 수행한다.")
            }
        }
    }
    job.join()
    println("모든 작업 완료")
}

println("async에서의 launch")
runBlocking {
    val scope = CoroutineScope(Dispatchers.Default + Job())

    val deferred: Deferred<String> = scope.async {
        delay(500L)
        throw IndexOutOfBoundsException("데이터가 존재하지 않다.")
    }

    try {
        val result = deferred.await()
        println("결과: $result")
    } catch (e: IndexOutOfBoundsException) {
        println("async 작업 실패: ${e.message}")
    }
    println("실험 종료")
}

println()

println()
println("전역에서의 예외 잡기")
runBlocking {
    val handler = CoroutineExceptionHandler { coroutineContext, exception ->
        // 이 람다는 처리되지 않은 예외가 발생했을 때 호출된다.
        println("처리되지 않은 예외 발생! context: $coroutineContext, exception: $exception")
    }

    // 2. 스코프 생성 시 컨텍스트에 핸들러 추가
    val scope = CoroutineScope(Dispatchers.Default + handler)

    // 3. 예외를 발생시키는 코루틴 실행
    scope.launch {
        throw ArithmeticException("0으로 나눌 수 없다!")
    }
}
println()
println("코루틴의 취소와 협력에 대해서")
println()
val scope3 = CoroutineScope(Dispatchers.Default + Job())
runBlocking {
    val job = scope3.launch {
        repeat(100) { i ->
            println("scope3 반복 작업 중... $i")
            delay(500L) // 이 함수가 취소 요청을 확인하는 '협력 지점'이 된다.
        }
    }
    delay(1200L) // 1.2초 후

    job.cancel() // 취소 요청
    println("Coroutine 취소 ")
}

println()
println("isActive 조절을 통한 코루틴 제거")
println()
runBlocking {
    val job = launch(Dispatchers.Default) {
        var i = 0
        // while문의 조건으로 isActive를 직접 확인한다.
        while (i < 5 && isActive) {
            Thread.sleep(1000L)
            i++
            println("작업 진행 중... $i")
        }
    }
    delay(2500L)
    job.cancel()
    job.join()
    println("작업 종료")
}


println()
println("취소시 리소스 정리하기")
println()

runBlocking {
    val job = launch {
        try {
            println("리소스 획득!")
            repeat(1000) { i ->
                println("작업 중... $i")
                delay(500L)
            }
        } finally {
            // 이 블록은 코루틴이 취소될 때 반드시 실행된다.
            println("리소스 정리 완료!")
        }
    }

    delay(1300L)
    job.cancelAndJoin() // cancel()과 join()을 한번에 호출
    println("메인: 코루틴 취소 완료")
}

println()
println("with Context를 활용한 실행 컨텍스트 전환")
println()

suspend fun fetchUserData1(): String {
    // 현재 스레드가 무엇이든 간에,
    // 이 블록 안에서는 Dispatchers.IO 스레드에서 실행됨을 보장받는다.
    val userName = withContext(Dispatchers.IO) {
        println("네트워크 요청 시작: ${Thread.currentThread().name}")
        delay(1000L) // 실제 네트워크 요청을 흉내
        "홍길동" // 이 값이 withContext의 반환 값이 됨
    }

    // withContext가 끝나면 원래 스레드로 돌아온다.
    println("원래 스레드로 복귀: ${Thread.currentThread().name}")
    return userName
}

runBlocking {
    println("작업 시작: ${Thread.currentThread().name}")
    val user = fetchUserData1()
    println("획득한 사용자: $user")
}

println()
println("순차 context 전환 테스트 -> async는 동시 실행한다")
println()

// 1. IO 스레드를 사용하는 suspend 함수
suspend fun fetchNetworkData(): String {
    println("  -> [함수 진입] 현재 스레드: ${Thread.currentThread().name}")

    // withContext를 만나면 잠시 IO 스레드로 넘어갑니다.
    val result = withContext(Dispatchers.IO) {
        println("  -> [withContext 내부] 📡 네트워크 요청 중... (스레드: ${Thread.currentThread().name})")
        delay(1000L) // 1초 소요
        "네트워크 데이터"
    }

    // withContext 블록이 끝나면 귀신같이 원래 스레드로 돌아옵니다.
    println("  -> [함수 종료 전] 원래 스레드로 복귀: ${Thread.currentThread().name}")
    return result
}

// 2. Default 스레드를 사용하는 suspend 함수
suspend fun calculateHeavyMath(): Int {
    println("  -> [함수 진입] 현재 스레드: ${Thread.currentThread().name}")

    // withContext를 만나면 잠시 Default(CPU 연산용) 스레드로 넘어갑니다.
    val result = withContext(Dispatchers.Default) {
        println("  -> [withContext 내부] 💻 복잡한 연산 중... (스레드: ${Thread.currentThread().name})")
        delay(1000L) // 1초 소요
        42
    }

    println("  -> [함수 종료 전] 원래 스레드로 복귀: ${Thread.currentThread().name}")
    return result
}

runBlocking {
    println("🚀 메인 작업 시작 (스레드: ${Thread.currentThread().name})\n")

    val totalTime = measureTimeMillis {
        // --- 첫 번째 작업 ---
        // async가 없으므로 여기서 코드가 멈추고(suspend) 끝날 때까지 기다립니다.
        println("--- 1. fetchNetworkData() 호출 ---")
        val data = fetchNetworkData()
        println("✅ 1번 결과 획득: $data (현재 스레드: ${Thread.currentThread().name})\n")

        // --- 두 번째 작업 ---
        // 1번 작업이 완전히 끝나야만 여기가 실행됩니다.
        println("--- 2. calculateHeavyMath() 호출 ---")
        val number = calculateHeavyMath()
        println("✅ 2번 결과 획득: $number (현재 스레드: ${Thread.currentThread().name})\n")
    }

    // 순차적으로 실행되었으므로 총 2초 이상 걸립니다.
    println("🏁 모든 작업 완료! 총 소요 시간: ${totalTime}ms (스레드: ${Thread.currentThread().name})")
}

println()
println("코루틴의 비동기 흐름: Flow")
println()

// 1초마다 1부터 3까지의 숫자를 방출하는 Flow를 생성하는 함수
fun simpleFlow(): Flow<Int> = flow {
    println("Flow가 시작되었다.")
    for (i in 1..3) {
        delay(1000L) // 1초 대기
        emit(i)      // 값을 방출
    }
}

runBlocking {
    println("메인: collect를 호출한다...")
    val flow = simpleFlow()

    // collect를 호출하는 순간, 비로소 flow { ... } 블록의 코드가 실행되기 시작한다.
    flow.collect { value ->
        println("메인: 수집된 값 -> $value")
    }

    println("메인: collect가 완료되었다.")
}

println()
println("코루틴의 비동기 흐름: Flow 종료 ")
println()

println()
println("코루틴의 asFlow 선언 ")
println()
runBlocking {
    (1..5).asFlow() // 1부터 5까지의 숫자를 방출하는 Flow 생성
        .filter { // 중간 연산자 1: 필터링
            println("Filter: $it")
            it % 2 != 0 // 홀수만 통과시킨다
        }
        .map { // 중간 연산자 2: 변환
            println("Map: $it")
            "숫자 [${it}]" // 숫자를 문자열로 변환한다
        }
        .collect { result -> // 종단 연산자: 최종 결과를 소비
            println("Collect: $result")
        }
}

println()
println(" 채널과 producer - consumer pattern에 대해서 ")
println()

println("생산자 소비자 패턴 기초")
runBlocking {
    val channel = Channel<Int>()

    // 생산자 코루틴
    launch {
        for (x in 1..5) {
            println("${x * x} 값을 채널에 보낸다...")
            channel.send(x * x) // 제곱한 값을 채널에 보냄
            delay(200L)
        }
        channel.close() // 모든 데이터를 보낸 후 채널을 닫는 것이 매우 중요!
    }

    // 소비자 코루틴
    // for-loop를 사용하여 채널을 소비하는 것이 가장 일반적인 패턴이다.
    // 채널이 close() 되면 루프는 자동으로 종료된다.
    for (receivedValue in channel) {
        println("...채널에서 ${receivedValue} 값을 받았다.")
    }

    println("모든 데이터 수신 완료!")
}

println("채널 버퍼링에 대해서")
// 기본적으로 Channel()에는 버퍼가 없다.
runBlocking {
    val channel = Channel<Int>(4)

    launch {
        for (x in 1..10) {
            println("Sending $x")
            channel.send(x) // 버퍼가 꽉 차면 여기서 일시 중단됨
        }
        channel.close()
    }
}