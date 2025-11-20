package org.example.behavioral.chainofresponsibility

/**
 * 요청을 처리할 수 있는 객체들을 체인으로 연결하여 요청이 처리될 때까지
 * 체인을 따라 전달하는 행위 패턴
 * 각 객체는 요청을 처리하거나 다음 객체로 전달 할 수 있음
 *
 * 사용하기 좋은 경우
 * - 여러 객체가 요청을 처리할 수 있고, 처리자가 미리 정해지지 않았을 때
 * - 요청을 보낸 쪽과 받는 쪽을 분리하고 싶을 때
 * - 처리 순서를 동적으로 변경하고 싶을 때
 * - 여러 단계의 검증이나 필터링이 필요할 때
 * - 미들웨어 패턴을 구현할 때
 *
 * 사용을 피하는 경우
 * - 처리자가 명확히 정해져 있을 때
 * - 모든 요청이 반드시 처리돼야 할 때
 * - 단순한 조건 분기만 필요할 때
 * - 성능이 매우 중요하고 체인 순회 오버헤드를 감당할 수 없을 때
 */

/**
 * 예제 코드 : 예외 처리
 */

// 1. 추상 핸들러 인터페이스
sealed class ExceptionHandler {
    protected var nextHandler: ExceptionHandler? = null

    fun setNext(handler: ExceptionHandler): ExceptionHandler {
        nextHandler = handler
        return handler
    }

    abstract fun handle(exception: Exception): String?
}

// 2. 구체적인 핸들러들
class NetworkExceptionHandler : ExceptionHandler() {
    override fun handle(exception: Exception): String? {
        return when (exception) {
            is java.net.UnknownHostException -> {
                "네트워크 연결을 확인해주세요. (NetworkHandler)"
            }

            is java.net.SocketTimeoutException -> {
                "요청 시간이 초과되었습니다. 다시 시도해주세요. (NetworkHandler)"
            }

            else -> nextHandler?.handle(exception)
        }
    }
}

class IOExceptionHandler : ExceptionHandler() {
    override fun handle(exception: Exception): String? {
        return when (exception) {
            is java.io.FileNotFoundException -> {
                "파일을 찾을 수 없습니다. (IOHandler)"
            }

            is java.io.IOException -> {
                "입출력 오류가 발생했습니다. (IOHandler)"
            }

            else -> nextHandler?.handle(exception)
        }
    }
}

class ValidationExceptionHandler : ExceptionHandler() {
    override fun handle(exception: Exception): String? {
        return when (exception) {
            is IllegalArgumentException -> {
                "잘못된 입력값입니다: ${exception.message} (ValidationHandler)"
            }

            is IllegalStateException -> {
                "잘못된 상태입니다: ${exception.message} (ValidationHandler)"
            }

            else -> nextHandler?.handle(exception)
        }
    }
}

class DefaultExceptionHandler : ExceptionHandler() {
    override fun handle(exception: Exception): String {
        return "알 수 없는 오류가 발생했습니다: ${exception.javaClass.simpleName} - ${exception.message} (DefaultHandler)"
    }
}

fun main() {
    // 체인 구성
    val handler = NetworkExceptionHandler().apply {
        setNext(IOExceptionHandler())
            .setNext(ValidationExceptionHandler())
            .setNext(DefaultExceptionHandler())
    }
    val exceptions = listOf(
        java.net.UnknownHostException("example.com"),
        java.io.FileNotFoundException("/path/to/file"),
        IllegalArgumentException("나이는 0보다 커야 합니다"),
        RuntimeException("예상치 못한 오류")
    )

    exceptions.forEach { exception ->
        val result = handler.handle(exception)
        println("예외: ${exception.javaClass.simpleName}")
        println("처리: $result")
        println()
    }
}