val favoriteFruits = listOf("사과", "딸기", "망고")

// "딸기"가 favoriteFruits 리스트에 포함되어 있는지 확인
val hasStrawberry = "딸기" in favoriteFruits
println("딸기를 좋아하나? ${hasStrawberry}") // 출력: 딸기를 좋아하나? true

// "포도"가 favoriteFruits 리스트에 포함되어 있는지 확인
val hasGrape = "포도" in favoriteFruits
println("포도를 좋아하나? ${hasGrape}") // 출력: 포도를 좋아하나? false

val userAge = 35

// 나이가 30대인지 확인
if (userAge in 30..39) {
    println("30대 사용자이다.")
}

val examScore = 75
// 80점 미만이면 불합격 처리
if (examScore in 0 until 80) {
    println("보충 학습이 필요하다.")
}

// 부정 !in 연산자
val guestCount = 12
val allowedRange = 1..10

// 손님 수가 허용 범위를 벗어났는지 확인
if (guestCount !in allowedRange) {
    println("경고: 수용 가능 인원을 초과했다!")
}

val bannedUsers = setOf("spammer1", "troll2")
val currentUser = "user123"

if (currentUser !in bannedUsers) {
    println("환영한다, ${currentUser}님!")
}

