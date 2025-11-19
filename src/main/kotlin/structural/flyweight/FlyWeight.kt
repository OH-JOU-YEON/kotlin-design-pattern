package org.example.structural.flyweight

/**
 * 많은 수의 객체를 관리하기 위해
 * 공유 가능한 상태, 공유 불가능한 상태 분리.
 * 메모리 사용 최소화
 *
 * 대량의 유사한 객체를 생성해야 할 때.
 * 메모리 부족 문제가 있을 때.
 * 객체의 대부분의 상태를 외부로 분리할 수 있을 때
 *
 * 불변 객체 권장 > 가변 객체 사용 시 원치 않은 변경이 일어날 수 있음
 *
 * 핵심 개념:
 *
 * Intrinsic State (내재 상태): 공유 가능, Flyweight 객체 내부에 저장
 * Extrinsic State (외재 상태): 공유 불가능, 클라이언트가 전달
 */


// 공유 가능한 상태
class TreeType(
    val name: String,
    val color: String,
    val texture: String
) {
    init {
        println("Creating TreeType: $name ($color)")
    }

    fun draw(canvas: String, x: Int, y: Int) {
        println("   Drawing $name tree at ($x, $y) on $canvas with texture: $texture")
    }
}

// 고유한 상태
class Tree(
    private val x: Int,
    private val y: Int,
    private val type: TreeType  // Flyweight 참조
) {
    fun draw(canvas: String) {
        type.draw(canvas, x, y)
    }
}

class TreeFactory {
    private val treeTypes = mutableMapOf<String, TreeType>()

    fun getTreeType(name: String, color: String, texture: String): TreeType {
        val key = "$name-$color-$texture"

        return treeTypes.getOrPut(key) {
            TreeType(name, color, texture)
        }
    }

    fun getTreeTypeCount() = treeTypes.size
}

// 숲 (클라이언트)
class Forest {
    private val trees = mutableListOf<Tree>()
    private val factory = TreeFactory()

    fun plantTree(x: Int, y: Int, name: String, color: String, texture: String) {
        val type = factory.getTreeType(name, color, texture)
        val tree = Tree(x, y, type)
        trees.add(tree)
    }

    fun draw(canvas: String) {
        println("\n🌲 Drawing forest on $canvas:")
        trees.forEach { it.draw(canvas) }
    }

    fun getMemoryUsage() {
        println("\nMemory Usage:")
        println("   Total trees: ${trees.size}")
        println("   Unique tree types: ${factory.getTreeTypeCount()}")
        println("   Memory saved by sharing tree types!")
    }
}

fun main() {
    println("=== Flyweight Pattern - Forest Simulation ===\n")

    val forest = Forest()

    // 많은 나무를 심지만, TreeType 객체는 몇 개만 생성됨
    println("--- Planting Trees ---")
    forest.plantTree(10, 20, "Oak", "Green", "oak_texture.png")
    forest.plantTree(30, 40, "Oak", "Green", "oak_texture.png")
    forest.plantTree(50, 60, "Oak", "Green", "oak_texture.png")
    forest.plantTree(70, 80, "Pine", "DarkGreen", "pine_texture.png")
    forest.plantTree(90, 100, "Pine", "DarkGreen", "pine_texture.png")
    forest.plantTree(110, 120, "Birch", "White", "birch_texture.png")

    // 숲 그리기
    forest.draw("MainCanvas")

    // 메모리 사용량
    forest.getMemoryUsage()
}