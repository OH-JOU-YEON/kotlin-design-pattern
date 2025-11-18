package org.example.creational.builder

/**
 * 빌더 패턴
 * -복잡한 객체를 단계적으로 생성할 수 있게 해줌.
 * 매개변수가 많을 때(4개 이상) 일때 유용하다.
 */

/**
 * 자바 스타일 빌더 패턴
 * 디폴트 값 초기화 > 검증 > setter 설정
 */

class Pizza private constructor(
    val size: String,
    val dough: String,
    val sauce: String,
    val cheese: Boolean,
    val pepperoni: Boolean,
    val bacon: Boolean,
    val mushrooms: Boolean,
    val onions: Boolean,
    val olives: Boolean
) {
    // 빌더 클래스 구현
    class Builder(private val size: String) {
        // 디폴트 값으로 초기화
        private var dough: String = "regular"
        private var sauce: String = "tomato"
        private var cheese: Boolean = false
        private var pepperoni: Boolean = false
        private var bacon: Boolean = false
        private var mushrooms: Boolean = false
        private var onions: Boolean = false
        private var olives: Boolean = false

        // 값을 수정하는 메서드
        fun dough(dough: String) = apply { this.dough = dough }
        fun sauce(sauce: String) = apply { this.sauce = sauce }
        fun cheese(cheese: Boolean) = apply { this.cheese = cheese }
        fun pepperoni(pepperoni: Boolean) = apply { this.pepperoni = pepperoni }
        fun bacon(bacon: Boolean) = apply { this.bacon = bacon }
        fun mushrooms(mushrooms: Boolean) = apply { this.mushrooms = mushrooms }
        fun onions(onions: Boolean) = apply { this.onions = onions }
        fun olives(olives: Boolean) = apply { this.olives = olives }

        fun build(): Pizza {
            // 유효성 검증
            require(size in listOf("Small", "Medium", "Large")) {
                "Size must be Small, Medium, or Large"
            }

            return Pizza(
                size = size,
                dough = dough,
                sauce = sauce,
                cheese = cheese,
                pepperoni = pepperoni,
                bacon = bacon,
                mushrooms = mushrooms,
                onions = onions,
                olives = olives
            )
        }
    }

    override fun toString(): String {
        val toppings = mutableListOf<String>()
        if (cheese) toppings.add("Cheese")
        if (pepperoni) toppings.add("Pepperoni")
        if (bacon) toppings.add("Bacon")
        if (mushrooms) toppings.add("Mushrooms")
        if (onions) toppings.add("Onions")
        if (olives) toppings.add("Olives")

        return """
            🍕 Pizza:
               Size: $size
               Dough: $dough
               Sauce: $sauce
               Toppings: ${toppings.joinToString(", ").ifEmpty { "None" }}
        """.trimIndent()
    }
}

// 사용
fun main() {
    val pizza1 = Pizza.Builder("Large")
        .dough("thin")
        .sauce("tomato")
        .cheese(true)
        .pepperoni(true)
        .mushrooms(true)
        .build()

    println(pizza1)

    println()

    val pizza2 = Pizza.Builder("Medium")
        .dough("thick")
        .cheese(true)
        .bacon(true)
        .onions(true)
        .olives(true)
        .build()

    println(pizza2)
}