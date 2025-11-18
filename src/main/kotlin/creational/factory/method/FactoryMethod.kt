package org.example.creational.factory.method

/**
 *팩터리 메서드 패턴
 *
 * 분기 판단 논리 대신 다형성 사용 원리
 * 객체 생성을 서브 클래스에 위임하는 패턴
 * 부모 클래스는 객체 생성 인터페이스 정의, 자식이 생성할 객체의 타입을 정함
 */

/**
 * 사용 경우
 *
 * 어떤 클래스가 생성될 지 미리 알 수 없을 때
 * 클래스 생성과 사용을 분리하고 싶을 때
 * 서브 클래스에서 객체 생성 방법을 재정의하고 싶을 대
 * 프레임워크나 라이브러리에서 확장 포인트를 제공하고 싶을 때
 *
 * 피할 경우
 * 제품 종류가 적고 변경 가능성이 낮을 때 > 단순 팩터리
 * 여러 제품 군을 함께 생성해야 할 때 > 추상 팩터리
 */

/**
 * 팩터리 클래스 객체 생성 부분이 인터페이스와 결합되는 것을 막기 위해
 * 단순 팩터리를 팩터리 클래스의 객체 생성에 도입하는 로그 예제
 */

interface LogMessage {
    fun format(message: String): String
    fun write(message: String)
}

// 구체적인 제품들
class InfoLogMessage : LogMessage {
    override fun format(message: String) = "[INFO] $message"
    override fun write(message: String) = println(format(message))
}

class WarningLogMessage : LogMessage {
    override fun format(message: String) = "[WARNING] $message"
    override fun write(message: String) = println(format(message))
}

class ErrorLogMessage : LogMessage {
    override fun format(message: String) = "[ERROR] $message"
    override fun write(message: String) = println(format(message))
}

class DebugLogMessage : LogMessage {
    override fun format(message: String): String = "[DEBUG] $message"
    override fun write(message: String) = println(format(message))
}

// 팩터리 메서드 패턴의 부모 클래스
abstract class Logger {
    abstract fun createLogMessage(): LogMessage

    fun log(message: String) {
        val logMessage = createLogMessage()
        logMessage.write(message)
    }
}

// 타입을 결정하는 서브클래스
class InfoLogger : Logger() {
    override fun createLogMessage(): LogMessage = InfoLogMessage()
}

class WarningLogger : Logger() {
    override fun createLogMessage(): LogMessage = WarningLogMessage()
}

class ErrorLogger : Logger() {
    override fun createLogMessage(): LogMessage = ErrorLogMessage()
}

class DebugLogger : Logger() {
    override fun createLogMessage(): LogMessage = DebugLogMessage()
}

// 단순 팩터리
class LoggerFactory {
    companion object {
        fun getLogger(level: String): Logger {
            return when (level.uppercase()) {
                "INFO" -> InfoLogger()
                "WARNING", "WARN" -> WarningLogger()
                "ERROR" -> ErrorLogger()
                "DEBUG" -> DebugLogger()
                else -> InfoLogger()
            }
        }
    }
}

fun main() {
    val infoLogger = LoggerFactory.getLogger("INFO")
    infoLogger.log("Application started")

    val warningLogger = LoggerFactory.getLogger("WARNING")
    warningLogger.log("Low disk space")

    val errorLogger = LoggerFactory.getLogger("ERROR")
    errorLogger.log("Connection failed")

    val debugLogger = LoggerFactory.getLogger("DEBUG")
    debugLogger.log("Variable x = 42")
}