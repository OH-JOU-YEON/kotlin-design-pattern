package org.example.behavioral.obsserver

/**
 * 객체의 상태 변화를 관찰하는 관찰자들에게 자동으로 알림을 보내는 행위 패턴
 * 일대다 의존 관계 정의
 *
 * 사용 경우
 * - 한 객체의 상태 변화가 다른 객체들에게 영향을 줄 때
 * - 느슨한 결합이 필요할 때
 * - 이벤트 기반 시스템을 만들 때
 * - 실시간 업데이트가 필요할 때(주식, 날씨, 체팅)
 * - MVC 패턴의 모델 - 뷰 관계를 구현할 때
 *
 * 사용을 피하는 경우
 * - 관찰자가 적거나 고정적
 * - 순서가 중요한 알림일 때(옵서버 호출 순서는 보장되지 않음)
 * - 성능이 매우 중요하고 알림 오버헤드를 감당할 수 없을 때
 * - 복잡한 의존 관계로 예측하기 어려울 때
 */

/**
 * 예제 - 체팅 시스템
 */

data class ChatMessage(
    val from: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

interface ChatRoomObserver {
    fun onMessageReceived(message: ChatMessage)
    fun onUserJoined(username: String)
    fun onUserLeft(username: String)
}

class ChatRoom(val name: String) {
    private val participants = mutableMapOf<String, ChatRoomObserver>()
    private val messageHistory = mutableListOf<ChatMessage>()

    fun join(username: String, observer: ChatRoomObserver) {
        participants[username] = observer
        println("$username joined $name")

        participants.forEach { (user, obs) ->
            if (user != username) {
                obs.onUserJoined(username)
            }
        }

        // 최근 메세지 히스토리 전송
        messageHistory.takeLast(10).forEach { message ->
            observer.onMessageReceived(message)
        }
    }

    fun leave(username: String) {
        participants.remove(username)
        println("$username left $name")

        participants.values.forEach { observer ->
            observer.onUserLeft(username)
        }
    }

    fun sendMessage(from: String, content: String) {
        if (!participants.containsKey(from)) {
            println("$from is not in the room")
            return
        }

        val message = ChatMessage(from, content)
        messageHistory.add(message)

        // 모든 참가자에게 메시지 전송
        participants.forEach { (username, observer) ->
            observer.onMessageReceived(message)
        }
    }

    fun getParticipantCount() = participants.size
}

class User(private val username: String) : ChatRoomObserver {
    private val receivedMessages = mutableListOf<ChatMessage>()

    override fun onMessageReceived(message: ChatMessage) {
        receivedMessages.add(message)

        if (message.from != username) {
            println("$username received: [${message.from}] ${message.content}")
        }
    }

    override fun onUserJoined(username: String) {
        println("$this.username: $username joined the room")
    }

    override fun onUserLeft(username: String) {
        println("$this.username: $username left the room")
    }

    fun getMessageCount() = receivedMessages.size
}

class ChatBot(private val name: String) : ChatRoomObserver {
    private val keywords = mapOf(
        "help" to "I'm here to help! Type 'commands' to see available commands.",
        "commands" to "Available commands: help, time, users",
        "time" to "Current time: ${java.time.LocalDateTime.now()}"
    )

    override fun onMessageReceived(message: ChatMessage) {
        // 봇이 언급되었는지 확인
        if (message.content.contains("@$name", ignoreCase = true)) {
            println("$name: Processing message from ${message.from}")

            // 키워드 확인
            keywords.forEach { (keyword, response) ->
                if (message.content.contains(keyword, ignoreCase = true)) {
                    println("$name: $response")
                }
            }
        }
    }

    override fun onUserJoined(username: String) {
        println("$name: Welcome, $username!")
    }

    override fun onUserLeft(username: String) {
        println("$name: Goodbye, $username!")
    }
}

class ChatLogger(private val roomName: String) : ChatRoomObserver {
    private val logs = mutableListOf<String>()

    override fun onMessageReceived(message: ChatMessage) {
        val log = "[${java.time.Instant.ofEpochMilli(message.timestamp)}] ${message.from}: ${message.content}"
        logs.add(log)
    }

    override fun onUserJoined(username: String) {
        val log = "[${java.time.LocalDateTime.now()}] $username joined"
        logs.add(log)
    }

    override fun onUserLeft(username: String) {
        val log = "[${java.time.LocalDateTime.now()}] $username left"
        logs.add(log)
    }

    fun printLogs() {
        println("\nChat Logs for $roomName:")
        logs.forEach { println("   $it") }
    }
}


fun main() {
    println("=== Observer Pattern - Chat System ===\n")

    val chatRoom = ChatRoom("General")

    // 사용자 및 봇 생성
    val alice = User("Alice")
    val bob = User("Bob")
    val charlie = User("Charlie")
    val helpBot = ChatBot("HelpBot")
    val logger = ChatLogger("General")

    // 채팅방 참가
    println("--- Users Joining ---")
    chatRoom.join("Alice", alice)
    chatRoom.join("HelpBot", helpBot)
    chatRoom.join("Logger", logger)

    println("\n" + "=".repeat(60))

    chatRoom.join("Bob", bob)

    println("\n" + "=".repeat(60))

    chatRoom.join("Charlie", charlie)

    // 메시지 전송
    println("\n" + "=".repeat(60) + "\n--- Chatting ---")
    chatRoom.sendMessage("Alice", "Hello everyone!")

    println("\n" + "=".repeat(60))
    chatRoom.sendMessage("Bob", "Hi Alice! How are you?")

    println("\n" + "=".repeat(60))
    chatRoom.sendMessage("Charlie", "@HelpBot help")

    println("\n" + "=".repeat(60))
    chatRoom.sendMessage("Alice", "@HelpBot what's the time?")

    println("\n" + "=".repeat(60))
    chatRoom.sendMessage("Bob", "I have to go, bye!")

    // 채팅방 나가기
    println("\n" + "=".repeat(60) + "\n--- User Leaving ---")
    chatRoom.leave("Bob")

    println("\n" + "=".repeat(60))
    chatRoom.sendMessage("Alice", "Bob left")

    // 로그 출력
    logger.printLogs()

    println("\nRoom Statistics:")
    println("   Current participants: ${chatRoom.getParticipantCount()}")
    println("   Alice received: ${alice.getMessageCount()} messages")
}