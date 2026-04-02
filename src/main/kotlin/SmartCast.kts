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

val MyCircle = Circle(10.0)
val MyRectangle = Rectangle(10.0, 10.0)
printArea(MyCircle)
println()
printArea(MyRectangle)
