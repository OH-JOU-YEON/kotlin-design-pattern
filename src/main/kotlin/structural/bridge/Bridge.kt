package org.example.Structural.bridge

/**
 * 브리지 패턴 > 추상과 구현을 분리해서 각각 독립적으로 변경하게 하는 구조 패턴
 *
 * 추상 클래스(구현체의 인터페이스)
 * 구현체
 */

// 문제: 플랫폼(Windows, Mac) x 앱(메일, 메모) = 4개 클래스
//abstract class App
//class WindowsMailApp : App()
//class MacMailApp : App()
//class WindowsNoteApp : App()
//class MacNoteApp : App()

// 새로운 플랫폼(Linux) 추가 → 2개 클래스 더 필요
// 새로운 앱(Todo) 추가 → 3개 클래스 더 필요
// n개 플랫폼 x m개 앱 = n*m개 클래스!

// 해결: 추상과 구현 분리
//abstract class App(val platform: Platform)
//class MailApp(platform: Platform) : App(platform)
//class NoteApp(platform: Platform) : App(platform)

//interface Platform
//class WindowsPlatform : Platform
//class MacPlatform : Platform

/**
 * 예제: 리모컨과 디바이스
 */

// 구현체의 인터페이스
interface Device {
    fun isEnabled(): Boolean
    fun enable()
    fun disable()
    fun getVolume(): Int
    fun setVolume(percent: Int)
    fun getChannel(): Int
    fun setChannel(channel: Int)
}

// 구현체들
class TV : Device {
    private var on = false
    private var volume = 30
    private var channel = 1

    override fun isEnabled() = on

    override fun enable() {
        on = true
        println("TV is now ON")
    }

    override fun disable() {
        on = false
        println("TV is now OFF")
    }

    override fun getVolume() = volume

    override fun setVolume(percent: Int) {
        volume = percent.coerceIn(0, 100)
        println("TV volume set to $volume%")
    }

    override fun getChannel() = channel

    override fun setChannel(channel: Int) {
        this.channel = channel
        println("TV channel set to $channel")
    }
}

class Radio : Device {
    private var on = false
    private var volume = 50
    private var frequency = 88

    override fun isEnabled() = on

    override fun enable() {
        on = true
        println("Radio is now ON")
    }

    override fun disable() {
        on = false
        println("Radio is now OFF")
    }

    override fun getVolume() = volume

    override fun setVolume(percent: Int) {
        volume = percent.coerceIn(0, 100)
        println("Radio volume set to $volume%")
    }

    override fun getChannel() = frequency

    override fun setChannel(channel: Int) {
        this.frequency = channel
        println("Radio frequency set to $frequency MHZ")
    }
}

// 추상 - 기본 리모컨
open class RemoteControl(protected val device: Device) {
    open fun togglePower() {
        if (device.isEnabled()) {
            device.disable()
        } else {
            device.enable()
        }
    }

    open fun volumeDown() {
        val currentVolume = device.getVolume()
        device.setVolume(currentVolume - 10)
    }

    open fun volumeUp() {
        val currentVolume = device.getVolume()
        device.setVolume(currentVolume + 10)
    }

    open fun channelDown() {
        val currentChannel = device.getChannel()
        device.setChannel(currentChannel - 1)
    }

    open fun channelUp() {
        val currentChannel = device.getChannel()
        device.setChannel(currentChannel + 1)
    }
}

class AdvancedRemoteControl(device: Device) : RemoteControl(device) {
    fun mute() {
        println("Muting device")
        device.setVolume(0)
    }

    fun setChannel(channel: Int) {
        println(" Directly setting channel to $channel")
        device.setChannel(channel)
    }
}

fun main() {
    println("=== Using Basic Remote with TV ===\n")
    val tv = TV()
    val tvRemote = RemoteControl(tv)

    tvRemote.togglePower()
    tvRemote.volumeUp()
    tvRemote.channelUp()
    tvRemote.channelUp()

    println("\n=== Using Advanced Remote with Radio ===\n")
    val radio = Radio()
    val radioRemote = AdvancedRemoteControl(radio)

    radioRemote.togglePower()
    radioRemote.volumeUp()
    radioRemote.setChannel(95)
    radioRemote.mute()

    println("\n=== Switching Remote Between Devices ===\n")
    val universalRemote = AdvancedRemoteControl(tv)
    universalRemote.togglePower()
    universalRemote.volumeDown()
    universalRemote.setChannel(10)
}