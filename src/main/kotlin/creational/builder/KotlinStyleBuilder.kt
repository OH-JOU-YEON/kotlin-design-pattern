package org.example.creational.builder

/**
 * 코틀린의 매개변수 디폴트 값 활용
 */

data class Pizza2(
    val size: String,
    val dough: String = "regular",
    val sauce: String = "tomato",
    val cheese: Boolean = false,
    val pepperoni: Boolean = false,
    val bacon: Boolean = false,
    val mushrooms: Boolean = false,
    val onions: Boolean = false,
    val olives: Boolean = false
) {
    init {
        require(size in listOf("Small", "Medium", "Large")) {
            "Size must be Small, Medium, or Large"
        }
    }
}

// 사용 - 빌더 패턴 없이도 깔끔함
fun main() {
    val pizza1 = Pizza2(
        size = "Large",
        dough = "thin",
        cheese = true,
        pepperoni = true,
        mushrooms = true
    )

    println(pizza1)

    val pizza2 = Pizza2(
        size = "Medium",
        dough = "thick",
        cheese = true,
        bacon = true,
        onions = true,
        olives = true
    )

    println(pizza2)
}