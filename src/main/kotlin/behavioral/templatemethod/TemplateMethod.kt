package org.example.behavioral.templatemethod

/**
 * 알고리즘의 구조를 상위 클래스에서 정의하고 세부 구현은 하위 클래스에서 재정의하는 행위 패턴
 * - 알고리즘의 단계는 변경하지 않지만 일부 단계를 하위 클래스에서 구현
 *
 * 사용 경우
 * - 알고리즘의 구조는 같지만 세부 구현이 다를 때
 * - 중복 코드를 제거하고 싶을 때
 * - 하위 클래스의 확장 포인트를 제어하고 싶을 때
 * - 공통 로직을 한 곳에 모으고 싶을 때
 *
 * 사용을 피하는 경우
 * - 알고리즘의 단계가 자주 변경될 때
 * - 유연성이 매우 중요할 때 > 전략 패턴 고려
 * - 상속 계층이 복잡해질 때
 * - 조합이 더 적합할 때
 */

/**
 * 예제 코드 : 음료 만들기
 */

abstract class CaffeineBeverage {
    // 템플릿 메서드
    fun prepareRecipe() {
        boilWater()
        brew()
        pourInCup()

        // 훅 메서드 사용
        if (customerWantsCondiments()) {
            addCondiments()
        }
    }

    // 메서드 구현
    private fun boilWater() {
        println("Boiling water")
    }

    private fun pourInCup() {
        println("Pouring into cup")
    }

    // 추상 메서드
    protected abstract fun brew()
    protected abstract fun addCondiments()

    // 훅 메서드 - 선택적 오버라이드
    protected open fun customerWantsCondiments(): Boolean = true
}

class Tea : CaffeineBeverage() {
    override fun brew() {
        println("Steeping the tea bag")
    }

    override fun addCondiments() {
        println("Adding lemon")
    }
}

class Coffee : CaffeineBeverage() {
    override fun brew() {
        println("Dripping coffee through filter")
    }

    override fun addCondiments() {
        println("Adding sugar and milk")
    }

    // 훅 메서드 오버라이드
    override fun customerWantsCondiments(): Boolean {
        val answer = getUserInput()
        return answer.lowercase().startsWith("y")
    }

    private fun getUserInput(): String {
        print("Would you like milk and sugar with your coffee (y/n)? ")
        return readLine() ?: "n"
    }
}

class HotChocolate : CaffeineBeverage() {
    override fun brew() {
        println("Mixing hot chocolate powder")
    }

    override fun addCondiments() {
        println("Adding marshmallows")
    }
}

fun main() {
    println("=== Template Method Pattern - Beverages ===\n")

    println("--- Making Tea ---")
    val tea = Tea()
    tea.prepareRecipe()

    println("\n--- Making Coffee ---")
    val coffee = Coffee()
    coffee.prepareRecipe()

    println("\n--- Making Hot Chocolate ---")
    val hotChocolate = HotChocolate()
    hotChocolate.prepareRecipe()
}