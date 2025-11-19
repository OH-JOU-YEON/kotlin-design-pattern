package org.example.Structural.adapter

/**
 * 호환되지 않는 인터페이스를 가진 객체들이 함께 작동하도록 중간에서 변환
 *
 * 기존 클래스를 수정할 수 없고, 레거시 코드를 새로운 시스템과 통합할 때 사용
 *
 * 변환 로직이 너무 복잡하면 피한다.
 */

/**
 * 예제: 원형과 사각형 못
 */

interface RoundPeg {
    val radius: Double
    fun getRadius(): Double = radius
}

// 기존 클래스
class SimpleRoundPeg(override val radius: Double) : RoundPeg

// 원형 구멍
class RoundHole(private val radius: Double) {
    fun fits(peg: RoundPeg): Boolean {
        val fits = radius >= peg.getRadius()
        println("Hole(r=$radius) ${if (fits) "fits" else " doesn't fit"} Peg(r=${peg.getRadius()})")
        return fits
    }
}

// 호환 안 되는 클래스
class SquarePeg(val width: Double) {
    fun getWidth(): Double = width
}

// 어댑터
class SquarePegAdapter(private val squarePeg: SquarePeg) : RoundPeg {
    override val radius: Double
        get() {
            val diagonal = squarePeg.getWidth() * Math.sqrt(2.0)
            return diagonal / 2.0
        }
}

// 사용
fun main() {
    val hole = RoundHole(5.0)
    val squarePeg1 = SquarePeg(5.0)
    val squarePeg2 = SquarePeg(7.0)

    println("\n=== Testing Square Pegs (with adapter) ===\n")
    val adapter1 = SquarePegAdapter(squarePeg1)
    val adapter2 = SquarePegAdapter(squarePeg2)

    println("Square peg width: ${squarePeg1.getWidth()}, converted radius: ${adapter1.radius}")
    hole.fits(adapter1)

    println("Square peg width: ${squarePeg2.getWidth()}, converted radius: ${adapter2.radius}")
    hole.fits(adapter2)
}