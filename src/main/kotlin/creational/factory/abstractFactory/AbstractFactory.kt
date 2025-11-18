package org.example.creational.factory.abstractFactory

/**
 * 추상 팩터리 패턴이란?
 * 특정 제품들을 제공할 때 사용하는 패턴
 * 구체적인 클래스를 지정하지 않고도 연관되거나 의존된 조합을 만들 수 있음
 *
 * 사용 경우
 * 클라이언트가 구현을 알 필요 없을 때
 */

/**
 * 예제: 테마 매니저
 */

interface Color {
    val background: String
    val text: String
    val accent: String
}

interface Font {
    val family: String
    val size: Int
}

interface Icon {
    fun render(name: String): String
}

// Light 테마 제품군
class LightColor : Color {
    override val background = "#FFFFFF"
    override val text = "#000000"
    override val accent = "#007AFF"
}

class LightFont : Font {
    override val family = "Roboto"
    override val size = 14
}

class LightIcon : Icon {
    override fun render(name: String) = "Light $name icon"
}

// Dark 테마 제품군
class DarkColor : Color {
    override val background = "#1E1E1E"
    override val text = "#FFFFFF"
    override val accent = "08A84FF"
}

class DarkFont : Font {
    override val family = "Roboto"
    override val size = 14
}

class DarkIcon : Icon {
    override fun render(name: String) = "Dark $name icon"
}

//대비가 강한 제품들
class HighContrastColor : Color {
    override val background = "#000000"
    override val text = "#FFFF00"
    override val accent = "#00FF00"
}

class HighContrastFont : Font {
    override val family = "Arial"
    override val size = 16
}

class HighContrastIcon : Icon {
    override fun render(name: String) = "HighContrast $name icon"
}

// 추상 팩터리 패턴
interface ThemeFactory {
    fun createColor(): Color
    fun createFont(): Font
    fun createIcon(): Icon
}

// 테마묶음
class LightThemeFactory : ThemeFactory {
    override fun createColor(): Color = LightColor()
    override fun createFont(): Font = LightFont()
    override fun createIcon(): Icon = LightIcon()
}

class DarkThemeFactory : ThemeFactory {
    override fun createColor(): Color = DarkColor()
    override fun createFont(): Font = DarkFont()
    override fun createIcon(): Icon = DarkIcon()
}

class HighContrasThemeFactory : ThemeFactory {
    override fun createColor(): Color = HighContrastColor()
    override fun createFont(): Font = HighContrastFont()
    override fun createIcon(): Icon = HighContrastIcon()
}

//클라이언트 코드
class UIRenderer(private val themeFactory: ThemeFactory) {
    private val color = themeFactory.createColor()
    private val font = themeFactory.createFont()
    private val icon = themeFactory.createIcon()

    fun renderUI() {
        println("\n=== Rendering UI ===")
        println("Background: ${color.background}")
        println("Text Color: ${color.text}")
        println("Accent Color: ${color.accent}")
        println("Font: ${font.family}, ${font.size}px")
        println(icon.render("home"))
        println(icon.render("settings"))
    }
}

// 테마 매니저
class ThemeManager {
    private var currentTheme: String = "light"

    fun setTheme(theme: String) {
        currentTheme = theme
    }

    fun getThemeFactory(): ThemeFactory {
        return when (currentTheme.lowercase()) {
            "light" -> LightThemeFactory()
            "dark" -> DarkThemeFactory()
            "high-contrast" -> HighContrasThemeFactory()
            else -> LightThemeFactory()
        }
    }
}

fun main() {
    val themeManager = ThemeManager()

    // Light 테마
    themeManager.setTheme("light")
    var renderer = UIRenderer(themeManager.getThemeFactory())
    renderer.renderUI()

    // Dark 테마
    themeManager.setTheme("dark")
    renderer = UIRenderer(themeManager.getThemeFactory())
    renderer.renderUI()

    // High Contrast 테마
    themeManager.setTheme("high-contrast")
    renderer = UIRenderer(themeManager.getThemeFactory())
    renderer.renderUI()
}