package org.example.Structural.facade

/**
 * 내부 구조를 숨기고 인터페이스만 노출
 *
 * 사용 경우
 * - 서브 시스템이 복잡할 때
 * - 의존성을 줄이고 싶을 때
 * - 레거시 코드를 감싸고 싶을 때
 */

/**
 * 예제코드 - 가전제품
 */

class Amplifier {
    fun on() = println("Amplifier ON")
    fun off() = println("Amplifier OFF")
    fun setVolume(level: Int) = println("Volume set to $level")
    fun setSurroundSound() = println("Surround sound ON")
}

class DvdPlayer {
    fun on() = println("DVD Player ON")
    fun off() = println("DVD Player OFF")
    fun play(movie: String) = println("Playing '$movie'")
    fun stop() = println("Stopped")
    fun eject() = println("Ejecting disc")
}

class Projector {
    fun on() = println("Projector ON")
    fun off() = println("Projector OFF")
    fun wideScreenMode() = println("Wide screen mode")
}

class TheaterLights {
    fun dim(level: Int) = println("Lights dimmed to $level%")
    fun on() = println("Lights ON")
}

class Screen {
    fun down() = println("Screen going down")
    fun up() = println("Screen going up")
}

class PopcornPopper {
    fun on() = println("Popcorn popper ON")
    fun off() = println("Popcorn popper OFF")
    fun pop() = println("Popping popcorn!")
}

// 퍼사드 패턴
class HomeTheaterFacade(
    private val amplifier: Amplifier = Amplifier(),
    private val dvdPlayer: DvdPlayer = DvdPlayer(),
    private val projector: Projector = Projector(),
    private val lights: TheaterLights = TheaterLights(),
    private val screen: Screen = Screen(),
    private val popper: PopcornPopper = PopcornPopper()
) {
    fun watchMovie(movie: String) {
        println("\nGet ready to watch a movie...\n")

        popper.on()
        popper.pop()
        lights.dim(10)
        screen.down()
        projector.on()
        projector.wideScreenMode()
        amplifier.on()
        amplifier.setSurroundSound()
        amplifier.setVolume(5)
        dvdPlayer.on()
        dvdPlayer.play(movie)

        println("\nEnjoy your movie!\n")

    }

    fun endMovie() {
        println("\nShutting movie theater down...\n")

        popper.off()
        lights.on()
        screen.up()
        projector.off()
        amplifier.off()
        dvdPlayer.stop()
        dvdPlayer.eject()
        dvdPlayer.off()

        println("\nMovie theater is off\n")
    }
}

fun main() {
    val homeTheater = HomeTheaterFacade()

    homeTheater.watchMovie("Inception")

    Thread.sleep(2000)

    homeTheater.endMovie()
}