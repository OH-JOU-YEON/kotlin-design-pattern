package org.example.creational.factory.simple

//봉인된 클래스로 제품 타입 정의
sealed class Shape {
    abstract fun draw()
    abstract fun area(): Double

    data class Circle(val radius: Double) : Shape() {
        override fun draw() = println("Drawing Circle with radius $radius")
        override fun area() = Math.PI * radius * radius
    }

    data class Rectangle(val width: Double, val height: Double) : Shape() {
        override fun draw() = println("Drawing Rectangle ${width}x${height}")
        override fun area() = width * height
    }

    data class Triangle(val base: Double, val height: Double) : Shape() {
        override fun draw() = println("Drawing Triangle base=$base, height =$height")
        override fun area() = (base * height) / 2
    }
}

// 팩터리 클래스
class ShapeFactory {
    fun createShape(type: String, vararg params: Double): Shape {
        return when (type.lowercase()) {
            "circle" -> {
                require(params.size == 1) { "Circle requires 1 parameter (radius)" }
                Shape.Circle(params[0])
            }

            "rectangle" -> {
                require(params.size == 2) { "Rectangle requires 2 parameters (width, height)" }
                Shape.Rectangle(params[0], params[1])
            }

            "triangle" -> {
                require(params.size == 2) { "Triangle requires 2 parameters (base, height)" }
                Shape.Triangle(params[0], params[1])
            }

            else -> throw IllegalArgumentException("Unknown shape type: $type")
        }
    }

    fun createCircle(radius: Double) = Shape.Circle(radius)
    fun createRectangle(width: Double, height: Double) = Shape.Rectangle(width, height)
    fun createTriangle(base: Double, height: Double) = Shape.Triangle(base, height)
}


fun main() {
    val factory = ShapeFactory()

    val circle = factory.createShape("circle", 5.0)
    circle.draw()
    println("Area: ${circle.area()}")

    val rectangle = factory.createShape("rectangle", 6.0, 4.0)
    rectangle.draw()
    println("Area: ${rectangle.area()}")
}