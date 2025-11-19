package org.example.Structural.decorater

/**
 * 객체에 동적으로 기능을 추가하는 패턴
 *
 * 상속 대신 조합으로 기능 확장
 *
 * 기본 객체를 매개변수로 받는 데코레이터를 구현해 기능을 추가한다.
 *
 * 기존 객체를 수정하지 않고 기능을 추가하고 싶을 때 사용한다.
 */

/**
 * 예제 코드 : 커피 주문
 */

interface Coffee {
    fun cost(): Double
    fun description(): String
}

// 기존 커피
class SimpleCoffee : Coffee {
    override fun cost() = 2.0
    override fun description() = "SimpleCoffee"
}

// 데커레이터 추상 클래스
abstract class CoffeeDecorator(
    private val coffee: Coffee
) : Coffee {
    override fun cost() = coffee.cost()
    override fun description() = coffee.description()
}

class Milk(coffee: Coffee) : CoffeeDecorator(coffee) {
    override fun cost() = super.cost() + 0.5
    override fun description() = super.description() + ", Milk"
}

class Sugar(coffee: Coffee) : CoffeeDecorator(coffee) {
    override fun cost() = super.cost() + 0.2
    override fun description() = super.description() + ", Sugar"
}

class WhippedCream(coffee: Coffee) : CoffeeDecorator(coffee) {
    override fun cost() = super.cost() + 0.7
    override fun description() = super.description() + ", Whipped Cream"
}

class Vanilla(coffee: Coffee) : CoffeeDecorator(coffee) {
    override fun cost() = super.cost() + 0.6
    override fun description() = super.description() + ", Vanilla"
}

class Caramel(coffee: Coffee) : CoffeeDecorator(coffee) {
    override fun cost() = super.cost() + 0.8
    override fun description() = super.description() + ", Caramel"
}

class ExtraShot(coffee: Coffee) : CoffeeDecorator(coffee) {
    override fun cost() = super.cost() + 1.0
    override fun description() = super.description() + ", Extra Shot"
}

fun main() {
    // 기본 커피
    var coffee: Coffee = SimpleCoffee()
    println("${coffee.description()}: $${coffee.cost()}")

    // 우유 추가
    coffee = Milk(coffee)
    println("${coffee.description()}: $${coffee.cost()}")

    // 설탕 추가
    coffee = Sugar(coffee)
    println("${coffee.description()}: $${coffee.cost()}")

    // 휘핑크림 추가
    coffee = WhippedCream(coffee)
    println("${coffee.description()}: $${coffee.cost()}")

    println("\n=== Creating Complex Coffee ===\n")

    // 복잡한 조합
    val specialCoffee = ExtraShot(
        Caramel(
            Vanilla(
                WhippedCream(
                    Milk(
                        SimpleCoffee()
                    )
                )
            )
        )
    )

    println("${specialCoffee.description()}: $${specialCoffee.cost()}")
}