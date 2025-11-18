package org.example.creational.prototype

/**
 * 객체 생성 비용이 클 때, 기존 객체를 복사하여 새 객체를 생성한다.
 * 복사 깊이에 주의한다.
 *
 * 같은 객체를 많이 만들어야 하고, 객체 생성 로직이 단순할 때 사용한다.
 */

/**
 * 코틀린의 경우, 얕은 복사는 copy 활용, 깊은 복사는 직접 구현
 */

data class User(val name: String, val age: Int)

val user1 = User("John", 30)
val user2 = user1.copy(name = "Jane") // 간단한 복제

// 깊은 복사
data class Team(val name: String, val members: MutableList<String>) {
    fun deepCopy(): Team {
        return Team(name, members.toMutableList())
    }
}

// 3. Cloneable 인터페이스 대신 커스텀 인터페이스 사용
interface Prototype<T> {
    fun clone(): T
}

