#!/usr/bin/env kotlin

fun getLength(text: String): Int {
    // text 파라미터는 Non-Null 타입으로 선언되었으므로,
    // NPE 걱정 없이 안전하게 .length를 호출할 수 있다.
    return text.length
}


fun getLength(text: String?): Int? {
    // return text.length // 컴파일 오류!
    // 컴파일러가 "text가 null이면 어떡할 건데?"라며 이 코드를 막아섭니다.
    // NPE가 발생할 수 있는 위험한 코드이다.

    return text?.length
}

var name: String? = "Kotlin"

// name이 null이 아니므로, .length가 호출되고 결과는 Int? 타입인 6이 된다.
val length: Int? = name?.length
println("이름의 길이: $length")

name = null

// name이 null이므로, .length는 호출되지 않고 표현식 전체가 null이 된다.
val length2: Int? = name?.length
println("이름의 길이: $length2")

// 학생은 소속된 학과가 없을 수도 있고, 학과에는 지도교수가 없을 수도 있다.
class Professor(val name: String)
class Department(val headProfessor: Professor?)
class Student(val department: Department?)

fun getProfessorName(student: Student): String? {
    // student -> department -> headProfessor -> name 순으로 안전하게 접근
    return student.department?.headProfessor?.name
}

val prof = Professor("김교수")
val compSci = Department(prof)
val studentA = Student(compSci)
println(getProfessorName(studentA)) // 출력: 김교수

val studentB = Student(Department(null)) // 지도교수가 없는 학과
println(getProfessorName(studentB)) // 출력: null

val studentC = Student(null) // 소속 학과가 없는 학생
println(getProfessorName(studentC)) // 출력: null

// let scope
// null이 아닐 경우, 특정 작업을 수행하고 싶을 때 ?.let 패턴을 매우 유용하게 사용할 수 있다.

name = "Gyeonggi-do, Seongnam-si"

name?.let {
    // 이 블록은 name이 null이 아닐 때만 실행된다.
    // 블록 안에서 'it'은 null이 아닌 String 타입으로 스마트 캐스팅된다.
    println("이름: $it")
    println("길이: ${it.length}")
    println("대문자: ${it.uppercase()}")
}

// 엘비스 연산자 ?:
// 아래는 불편하다
println("엘비스 연산자")
val name3: String? = null
var displayName3: String

if (name3 != null) {
    displayName3 = name3
} else {
    displayName3 = "Guest"
}

// ?:의 동작 방식 -> 왼쪽의 표현식이 null이 아니면 그 값을 그대로 사용해라.
println("엘비스 연산자 출력")
val name4: String? = null
val displayName4: String = name4 ?: "Guest" // name이 null이므로 "Guest"를 사용
println("Hello, $displayName4") // 출력: Hello, Guest

val realName: String? = "Alice"
val displayName2: String = realName ?: "Guest" // realName이 null이 아니므로 "Alice"를 사용
println("Hello, $displayName2") // 출력: Hello, Alice

println("안전한 호출 연산자 ?.와의 조합")
// Student -> Department -> Professor -> Name 구조를 다시 사용
val studentWithNoProfessor = Student(Department(null))

// 지도교수의 이름을 가져오되, 없으면 "담당 교수 미정"을 반환
val professorName = studentWithNoProfessor.department?.headProfessor?.name ?: "담당 교수 미정"

println("담당 교수: $professorName") // 출력: 담당 교수: 담당 교수 미정


println()
println("return과 함께 사용")

class Address(val street: String, val city: String)
class Customer(val address: Address?)

fun printShippingLabel(customer: Customer?) {
    // customer가 null이면 함수를 즉시 종료(return)
    val address = customer?.address ?: return

    // 이 줄부터 컴파일러는 address가 null이 아님을 알고 있다.
    println(address.street)
    println(address.city)
}
println("customorShippingLabel 출력")
printShippingLabel(null)
printShippingLabel(Customer(Address("테헤란로","서울")))


fun processPayment(amount: Double?) {
    // amount가 null이면 예외를 발생(throw)
    val validAmount = amount ?: throw IllegalArgumentException("결제 금액은 null일 수 없다.")

    // 이 줄부터 validAmount는 Double 타입으로 안전하게 사용 가능
    println("결제를 진행한다: $validAmount 원")
}
processPayment(10.0)
//processPayment(null)

println()
println()
println("위험한 캐스트")
println()
println()
val value: Any = "Hello"
val str: String = value as String // OK. value는 String이므로 성공

var anotherValue: Any = 123
// val str2: String = anotherValue as String // 런타임 오류! ClassCastException 발생
println("안전한 캐스트")
val anotherValue2: Any = 123

// anotherValue를 String으로 변환 시도.
// 실패했으므로 str2 변수에는 예외 대신 null이 할당된다.
val str2: String? = anotherValue2 as? String

println("변환 결과: $str2") // 출력: 변환 결과: null

println()
println("엘비스와의 조합")
val value1: Any = "I am a string"
val value2: Any = 123

// value1을 Int로 변환 시도, 실패하면 0을 기본값으로 사용
val intValue1 = value1 as? Int ?: 0

// value2를 Int로 변환 시도, 성공하면 그 값을 사용
val intValue2 = value2 as? Int ?: 0

println(intValue1) // 출력: 0
println(intValue2) // 출력: 123

// lateinit : 반드시 나중에 초기화 하겠다
// 안드로이드 Activity의 간단한 예시
println(
)
println("Late Init")
class TextView {
    var text: String = ""
}

object R {
    object id {
        const val my_text_view = 1
    }
}

class MyActivity {
    private lateinit var textView: TextView

    fun onCreate() {
        textView = findViewById(R.id.my_text_view)
        textView.text = "Hello Kotlin"
        println(textView.text)
    }

    private fun findViewById(id: Int): TextView {
        return when (id) {
            R.id.my_text_view -> TextView()
            else -> throw IllegalArgumentException("Unknown view id: $id")
        }
    }
}

val activity = MyActivity()
activity.onCreate()

println("By Lazy Strategy")


data class UserProfile(val id: String, val name: String, val email: String)

class UserSession(private val userId: String) {

    // by lazy를 사용한 지연 초기화 프로퍼티
    // 변수처럼 보이는데 사실 getUserProfile() 함수를 만드는 효과임
    // by lazy의 실제 동작을 C# 프로퍼티로 표현해 본다면:
//    private UserProfile _userProfile = null; // 초기엔 비어있음
//
//    public UserProfile UserProfile {
//        get {
//            // 1. 처음 접근했을 때 (null일 때)
//            if (_userProfile == null) {
//                Console.WriteLine("   [System] 데이터베이스에서 사용자 프로필을 로딩한다...");
//                _userProfile = loadProfileFromDB(userId); // 람다 블록 안의 코드 실행 및 저장
//            }
//            // 2. 두 번째부터는 저장된 값을 곧바로 반환
//            return _userProfile;
//        }
//    }
    val userProfile: UserProfile by lazy {
        println("   [System] 데이터베이스에서 사용자 프로필을 로딩한다... (비용이 큰 작업)")
        loadProfileFromDB(userId)
    }

    // 2. 반환 타입을 UserProfile로 지정하고, 가짜(Dummy) 데이터를 반환하도록 함수를 채웁니다.
    private fun loadProfileFromDB(userId: String): UserProfile {
        println("   [DB] SELECT * FROM users WHERE id = '$userId' 쿼리 실행 중...")
        // 실제로는 여기서 1~2초 걸리는 네트워크 작업이 일어난다고 상상해 주세요!
        return UserProfile(id = userId, name = "홍길동", email = "hong@example.com")
    }
}

println("--- 1. 세션 객체 생성 ---")
val session = UserSession("user123")
println("세션이 만들어졌지만, 아직 DB 조회는 일어나지 않았습니다.\n")

println("--- 2. 처음으로 userProfile.name 에 접근 ---")
// 이 순간, session 내부의 by lazy { ... } 블록이 최초로 1번 실행됩니다.
println("결과: 사용자 이름은 ${session.userProfile.name} 입니다.\n")

println("--- 3. 두 번째로 userProfile.email 에 접근 ---")
// 이미 위의 2번 과정에서 데이터를 가져와 세팅해 두었으므로,
// 이번에는 by lazy 블록이나 DB 조회가 실행되지 않고 바로 값을 출력합니다.
println("결과: 사용자 이메일은 ${session.userProfile.email} 입니다.")