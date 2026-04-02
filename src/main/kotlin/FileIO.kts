#!/usr/bin/env kotlin
import java.io.File

val file = File("hello.txt")
println(file.readLines())
// 한번에 읽기
val content: String = file.readText()
println(content)
// 파일을 줄단위로 읽기
val lines: List<String> = file.readLines()
lines.forEachIndexed { index, line ->
    println("${index + 1}: $line")
}

// 파일 쓰기
val outputFile = File("output.txt")
outputFile.writeText("나는 코틀린이다")
outputFile.appendText("\n새로운 줄을 추가한다.")

// 안전한 자원관리
println("안전한 자원관리")
// use 함수를 사용한 일반적인 파일 복사 예제
fun copyFile(sourcePath: String, destPath: String) {
    File(sourcePath).inputStream().use { input ->
        File(destPath).outputStream().use { output ->
            input.copyTo(output)
        }
    } // input과 output 스트림은 여기서 자동으로 닫힘
}