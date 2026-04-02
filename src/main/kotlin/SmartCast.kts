#!/usr/bin/env kotlin

// SmartCast

interface Shape {
    fun area(): Double
}
class Circle(val radius: Double) : Shape {

    override fun area() : Double = radius * radius
}
class Rectangle(val width: Double, val height: Double) : Shape
{
    override fun area(): Double = width * height
}

fun printArea(shape: Shape) {
    if (shape is Circle)
        println("circle radius : ${shape.radius}, circle area : ${shape.area()}")
    else if (shape is Rectangle)
        println("rectangle area : ${shape.width}, rectangle area : ${shape.area()}")
}

// when
fun getShapeDescription(shape: Shape): String {
    return when (shape) {
        // 각 분기마다 해당 타입으로 스마트 캐스팅이 일어난다.
        is Circle -> "반지름이 ${shape.radius}인 원"
        is Rectangle -> "너비 ${shape.width}, 높이 ${shape.height}의 사각형"
        else -> "알 수 없는 도형"
    }
}


// NULLTEST
fun printTextLength(text: String?) {
    if (text != null) {
        // text가 null이 아님이 확인된 이 블록 안에서,
        // text는 String?가 아닌 String 타입으로 취급된다!
        // 따라서 ?. 없이 안전하게 .length를 사용할 수 있다.
        println(text.length)
    } else {
        println("No text")
    }
}
val MyCircle = Circle(10.0)
val MyRectangle = Rectangle(10.0, 10.0)
printArea(MyCircle)
println()
printArea(MyRectangle)
println()
println("원 넓이 : ${getShapeDescription(MyCircle)}")
println()
println("사각형 넓이 : ${getShapeDescription(MyRectangle)}")
println()
println(printTextLength(null))
println()
