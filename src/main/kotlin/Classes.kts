#!/usr/bin/env kotlin
import java.time.LocalDate
println("클래스 선언")

// 프로퍼티
class Person {
    // 프로퍼티 정의
    var name: String = "익명" // 이름 (변경 가능)
    val birthYear: Int = 2000 // 태어난 해 (변경 불가능)

    fun introduce() {
        // 메서드 내부에서는 클래스의 프로퍼티에 직접 접근할 수 있다.
        println("안녕하라, 저는 ${name}이다. ${birthYear}년에 태어났다.")
    }
}
val person = Person()
person.name = "김코틀"
// 프로퍼티 값 읽기
println(person.name) // 출력: 익명
// var 프로퍼티 값 변경하기
person.name = "홍길동"
println(person.name) // 출력: 홍길동

person.introduce()

class SecondPerson(val name: String, val birthYear: Int) { // 주 생성자

    // 부 생성자
    constructor(name: String) : this(name, 2025) {
        // 부 생성자만의 추가적인 로직이 필요하다면 여기에 작성한다.
        println("부 생성자가 호출되었다. 기본 연도는 2025년이다.")
    }
}

println("주 생성자")
val person2 = SecondPerson("웅곤", 1987)
println("이름은 ${person2.name} 이고 ,  생년은 ${person2.birthYear} 이다")

val p2 = SecondPerson("Bob") // 부 생성자 호출
// 출력: 부 생성자가 호출되었다. 기본 연도는 2025년이다.
println("${p2.name} | ${p2.birthYear}")

class ThirdPerson(val name: String, val birthYear: Int) {
    val age: Int

    // 초기화 블록
    init {
        println("init 블록: ${name} 객체 생성을 시작한다.")

        // 1. 유효성 검사 로직
        if (birthYear > LocalDate.now().year) {
            throw IllegalArgumentException("출생 연도가 현재 연도보다 미래일 수 없다.")
        }

        // 2. 프로퍼티 계산 및 초기화
        age = LocalDate.now().year - birthYear + 1

        println("init 블록: ${name} 객체 생성이 완료되었다. 나이: $age")
    }
}
println("init 테스트")

val thirdPerson = ThirdPerson("Bob", 1987)
println("${thirdPerson.name}의 나이는 ${thirdPerson.age} 이다")

// 상속
// 자바와 달리, 코틀린의 모든 클래스와 메서드는 기본적으로 final이다.
open class Animal(val name: String) {

    // 메서드 또한 기본적으로 final이므로, 오버라이딩을 허용하려면 open을 붙여야 한다.
    open fun makeSound() {
        println("동물이 소리를 낸다.")
    }
}

println("클래스 상속하기")
class Dog(name: String) : Animal(name) {

    // Animal의 makeSound() 메서드를 재정의한다. override는 필수이다.
    override fun makeSound() {
        println("멍멍!")
    }
}

class Cat(name: String) : Animal(name) {

    override fun makeSound() {
        println("야옹~")
    }
}

println("다형성")
val myPet: Animal = Dog("해피")
val streetCat: Animal = Cat("길냥이")
myPet.makeSound()
streetCat.makeSound()

val animals: List<Animal> = listOf(Dog("멍멍이"), Cat("야옹이"), Dog("바둑이"))
for (animal in animals) {
    animal.makeSound() // 각 동물이 알아서 자신의 소리를 낸다.
}

println("추상클래스와 인터페이스")
// 추상메서드는 미완의 클래스다
// abstract 키워드로 추상 클래스 정의
abstract class AbstractAnimal(val name: String) {

    // 일반 메서드 (구현이 완료됨)
    fun eat() {
        println("${name}이(가) 밥을 먹다.")
    }

    // 추상 메서드 (본문이 없음)
    // 자식 클래스는 반드시 makeSound를 오버라이딩 해야 함
    abstract fun makeSound()
}

class DogFromAbstract(name: String) : AbstractAnimal(name) {
    override fun makeSound() {
        println("멍멍!")
    }
}

println("인터페이스")
// val animal = Animal("동물") // 컴파일 오류! 추상 클래스는 객체를 만들 수 없다.
val dog = DogFromAbstract("해피")
dog.eat()       // 부모로부터 물려받은 일반 메서드 호출
dog.makeSound() // 자식이 직접 구현한 추상 메서드 호출


interface Flyable {
    fun fly() // 본문이 없는 추상 메서드
}

interface Swimmable {
    // 기본 구현을 가진 디폴트 메서드
    fun swim() {
        println("첨벙첨벙 헤엄친다.")
    }
}

// 오리는 Animal을 상속받고, Flyable과 Swimmable 인터페이스를 모두 구현한다.
println("인터페이스는 다중상속 가능")
class Duck(name: String) : Animal(name), Flyable, Swimmable {
    override fun makeSound() {
        println("꽥꽥!")
    }

    override fun fly() {
        println("힘차게 날아오른다.")
    }

    // Swimmable의 swim()은 디폴트 구현이 있지만, 오리만의 방식으로 재정의할 수도 있다.
    override fun swim() {
        println("우아하게 물 위를 떠다닌다.")
    }
}

val duck = Duck("오리")
println("========")
println("인터페이스 오리 만들었음")
println("========")
duck.fly()
duck.swim()
duck.makeSound()


// 접근 제어자
// 기본이 public
class CoffeeMachine {
    private var waterLevel = 0

    // 외부에서는 이 private 메서드를 직접 호출할 수 없다.
    private fun boilWater() {
        println("물을 끓이다. (현재 수위: ${waterLevel})")
    }

    // public 메서드를 통해 내부 로직을 제어한다.
    fun brew() {
        if (waterLevel > 0) {
            boilWater()
            println("커피를 내린다.")
            waterLevel--
        }
    }

    fun addWater(amount: Int) {
        waterLevel += amount
    }
}
val machine = CoffeeMachine()
machine.addWater(2)
machine.brew()
// 불가능
// machine.waterLevel = 10 // 컴파일 오류! private 멤버에는 외부에서 접근 불가
// machine.boilWater()    // 컴파일 오류!
