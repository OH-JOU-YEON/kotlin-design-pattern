package org.example.iterator

/**
 * 컬렉션의 내부 구조를 노출하지 않고 순차적으로 요소에 접근할 수 있게 해주는 패턴
 * 컬렉션을 순회하는 방법을 캡슐화
 *
 * 코틀린 반복자 기능 이용
 */

/**
 * 예제 : 시퀀스 활용 지연 평가 반복자
 */

class LazyNumberGenerator(private val limit: Int) {

    // Sequence로 반복자 제공 (지연 평가)
    fun sequence(): Sequence<Int> = sequence {
        println("Sequence 시작")
        for (i in 1..limit) {
            println("$i 생성 중...")
            yield(i)  // 값을 하나씩 생성
        }
        println("Sequence 종료")
    }
}

// 피보나치 수열 생성기
fun fibonacciSequence(): Sequence<Long> = sequence {
    var a = 0L
    var b = 1L

    while (true) {
        yield(a)
        val next = a + b
        a = b
        b = next
    }
}

fun main() {
    println("=== 지연 평가 예제 ===")
    val generator = LazyNumberGenerator(5)
    val numbers = generator.sequence()
        .map { it * 2 }      // 아직 실행 안 됨
        .filter { it > 5 }   // 아직 실행 안 됨

    println("필터링 완료 (아직 값 계산 안 됨)")

    // 실제로 값을 요청할 때 계산됨
    println("\n결과:")
    for (num in numbers) {
        println(num)
    }

    println("\n=== 무한 시퀀스 예제 ===")
    val first10Fib = fibonacciSequence()
        .take(10)
        .toList()

    println("첫 10개 피보나치 수: $first10Fib")
}