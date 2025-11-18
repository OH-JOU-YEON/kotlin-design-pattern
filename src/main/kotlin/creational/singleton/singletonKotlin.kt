package org.example.creational.singleton

import java.util.concurrent.atomic.AtomicLong


/**
 * 스스로 공부한 내용과 책을 바탕으로 구현해본 열거형 싱글톤
 */

//val id = AtomicLong(0)
//
//public enum class IdGenerator(id: AtomicLong) {
//    INSTANCE(id)
//}


/**
 *  피드백을 받아 개선된 열거형 싱글톤
 */

enum class IdGenerator {
    INSTANCE;

    private val id = AtomicLong(0)

    fun generateId(): Long {
        return id.incrementAndGet()
    }

    fun getCurrentId(): Long {
        return id.get()
    }
}

/**
 * 코틀린에서 자주 사용하는 object 싱글톤
 */

object ObjectIdGenerator {
    private val id = AtomicLong(0)

    fun generateId(): Long = id.incrementAndGet()

    fun getCurrentId(): Long = id.get()
}


