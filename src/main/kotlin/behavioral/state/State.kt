package org.example.behavioral.state

/**
 * 객체 내부의 상태가 변경될 때, 객체의 행동을 변경하게 하는 패턴
 *
 * 각 상태를 별도의 클래스로 분리
 * 복잡한 분기를 상태 클래스로 대체
 *
 * 사용 경우
 * - 상태에 따라 행동이 크게 달라지는 경우
 * - 상태 전이 규칙이 복잡한 경우
 * - 큰 조건문이 상태에 의존하는 경우
 *
 * 사용을 피하는 경우
 * - 상태가 단순한 경우
 * - 상태전이가 거의 없는 경우
 * - 상태별 행동 차이가 미미한 경우
 */

/**
 * 예제 : 주문 처리 시스템
 */

sealed interface OrderState {
    fun process(order: Order): String
    fun cancel(order: Order): String
    fun ship(order: Order): String
    fun complete(order: Order): String
}

class Order(
    val orderId: String,
    private var state: OrderState = PendingState()
) {
    fun setState(newState: OrderState) {
        state = newState
        println("상태 변경: ${state::class.simpleName}")
    }

    fun getState(): OrderState = state

    fun process() = state.process(this)
    fun cancel() = state.cancel(this)
    fun ship() = state.ship(this)
    fun complete() = state.complete(this)
}

// 3. 구체적인 상태 클래스들

// 대기 상태
class PendingState : OrderState {
    override fun process(order: Order): String {
        order.setState(ProcessingState())
        return "주문 처리를 시작합니다."
    }

    override fun cancel(order: Order): String {
        order.setState(CancelledState())
        return "주문이 취소되었습니다."
    }

    override fun ship(order: Order): String {
        return "주문이 아직 처리되지 않아 배송할 수 없습니다."
    }

    override fun complete(order: Order): String {
        return "주문이 아직 처리되지 않아 완료할 수 없습니다."
    }
}

// 처리 중 상태
class ProcessingState : OrderState {
    override fun process(order: Order): String {
        return "이미 처리 중인 주문입니다."
    }

    override fun cancel(order: Order): String {
        order.setState(CancelledState())
        return "처리 중인 주문을 취소했습니다."
    }

    override fun ship(order: Order): String {
        order.setState(ShippedState())
        return "주문이 배송되었습니다."
    }

    override fun complete(order: Order): String {
        return "배송이 완료되지 않아 주문을 완료할 수 없습니다."
    }
}

// 배송 중 상태
class ShippedState : OrderState {
    override fun process(order: Order): String {
        return "이미 배송된 주문입니다."
    }

    override fun cancel(order: Order): String {
        return "배송된 주문은 취소할 수 없습니다. 반품을 이용해주세요."
    }

    override fun ship(order: Order): String {
        return "이미 배송 중입니다."
    }

    override fun complete(order: Order): String {
        order.setState(CompletedState())
        return "주문이 완료되었습니다."
    }
}

// 완료 상태
class CompletedState : OrderState {
    override fun process(order: Order): String {
        return "이미 완료된 주문입니다."
    }

    override fun cancel(order: Order): String {
        return "완료된 주문은 취소할 수 없습니다."
    }

    override fun ship(order: Order): String {
        return "이미 완료된 주문입니다."
    }

    override fun complete(order: Order): String {
        return "이미 완료된 주문입니다."
    }
}

// 취소 상태
class CancelledState : OrderState {
    override fun process(order: Order): String {
        return "취소된 주문은 처리할 수 없습니다."
    }

    override fun cancel(order: Order): String {
        return "이미 취소된 주문입니다."
    }

    override fun ship(order: Order): String {
        return "취소된 주문은 배송할 수 없습니다."
    }

    override fun complete(order: Order): String {
        return "취소된 주문은 완료할 수 없습니다."
    }
}

// 4. 사용 예제
fun main() {
    val order = Order("ORD-12345")

    println("=== 주문 처리 시나리오 1 ===")
    println(order.process())    // 대기 -> 처리 중
    println(order.ship())        // 처리 중 -> 배송 중
    println(order.complete())    // 배송 중 -> 완료
    println()

    println("=== 주문 처리 시나리오 2 (취소) ===")
    val order2 = Order("ORD-12346")
    println(order2.process())    // 대기 -> 처리 중
    println(order2.cancel())     // 처리 중 -> 취소
    println(order2.ship())       // 취소 상태에서 배송 시도
    println()

    println("=== 주문 처리 시나리오 3 (잘못된 순서) ===")
    val order3 = Order("ORD-12347")
    println(order3.ship())       // 대기 상태에서 바로 배송 시도
    println(order3.complete())   // 대기 상태에서 바로 완료 시도
}