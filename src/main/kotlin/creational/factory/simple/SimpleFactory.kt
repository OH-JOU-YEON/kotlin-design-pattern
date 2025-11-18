package org.example.creational.factory.simple

/**
 * 생성자의 생성 관련 로직이 지나치게 길어졌을 때,
 * 이를 별도의 클래스로 분리해 객체 생성만 담당하게 할 수 있다.
 *
 * 이를 단순 팩터리 패턴이라고 한다.
 */

/**
 * 사용하기 좋은 경우
 *
 * 객체 생성 로직이 복잡할 때
 *  - 생성자에 분기가 존재할 때
 * 여러 곳에서 같은 타입의 객체를 생성할 때
 * 생성하는 객체의 종류가 자주 변하지 않을 때
 *
 * 피해야 하는 경우
 *
 * 제품 종류가 매우 많고 계속 추가될 때
 * 제품군을 생성해야 할 때
 * 복잡한 객체를 단계적으로 생성해야 할 때
 */


/**
 * 기본적인 단순 팩터리 예제
 */

interface Pizza {
    fun prepare()
    fun bake()
    fun cut()
    fun box()
}

class CheesePizza : Pizza {
    override fun prepare() = println("Preparing Cheese Pizza")
    override fun bake() = println("Baking Cheese Pizza")
    override fun cut() = println("Cutting Cheese Pizza")
    override fun box() = println("Boxing Cheese Pizza")
}

class PepperoniPizza : Pizza {
    override fun prepare() = println("Preparing Pepperoni Pizza")
    override fun bake() = println("Baking Pepperoni Pizza")
    override fun cut() = println("Cutting Pepperoni Pizza")
    override fun box() = println("Boxing Pepperoni Pizza")
}

class VeggiePizza : Pizza {
    override fun prepare() = println("Preparing Veggie Pizza")
    override fun bake() = println("Baking Veggie Pizza")
    override fun cut() = println("Cutting Veggie Pizza")
    override fun box() = println("Boxing Veggie Pizza")
}


// 단순 팩터리 클래스

class SimplePizzaFactory {
    fun createPizza(type: String): Pizza {
        return when (type.lowercase()) {
            "cheese" -> CheesePizza()
            "pepperoni" -> PepperoniPizza()
            "veggie" -> VeggiePizza()
            else -> throw IllegalArgumentException("Unknown pizza type: $type")
        }
    }
}

// 피자 가게
class PizzaStore(private val factory: SimplePizzaFactory) {
    fun orderPizza(type: String): Pizza {
        val pizza = factory.createPizza(type)

        pizza.prepare()
        pizza.bake()
        pizza.cut()
        pizza.box()

        return pizza
    }
}

// 사용
fun main() {
    val factory = SimplePizzaFactory()
    val store = PizzaStore(factory)

    val cheesePizza = store.orderPizza("cheese")
    println()
    val pepperoniPizza = store.orderPizza("pepperoni")
}