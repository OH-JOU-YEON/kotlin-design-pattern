package org.example.structural.composite

/**
 * 객체들을 트리 구조로 구성.
 *  > 개별 객체와 복합 객체를 동일하게 취급할 수 있다.
 *
 *  사용 경우
 *  - 트리 구조 표현
 *  - 부분과 전체 동일 처리
 *  - 재귀적 구조 필요
 *  - 클라이언트가 개별 객체와 복합 객체를 구분않고 사용하길 원할 때
 *  - 계층적 데이터를 다룰 때
 *
 *  사용하지 않는 경우
 *  - 구조가 단순하거나 평면적일 때
 *  - 타입 안정성이 매우 중요할 때
 *  - 성능이 중요하고 재귀 오버헤드를 감당할 수 없을 때
 *  - 개별 객체와 복합 객체 다르게 처리해야 할 때
 *
 */

/**
 * 예제 코드 : 파일 시스템
 */

interface FileSystemComponent {
    fun getName(): String
    fun getSize(): Long
    fun display(indent: String = "")
}

class File(
    private val name: String,
    private val size: Long
) : FileSystemComponent {

    override fun getName() = name
    override fun getSize() = size

    override fun display(indent: String) {
        println("$indent $name ($size bytes)")
    }
}

// Composite - 디렉토리
class Directory(
    private val name: String
) : FileSystemComponent {

    private val children = mutableListOf<FileSystemComponent>()

    fun add(component: FileSystemComponent) {
        children.add(component)
    }

    fun remove(component: FileSystemComponent) {
        children.remove(component)
    }

    fun getChildren() = children.toList()

    override fun getName() = name

    override fun getSize(): Long {
        return children.sumOf { it.getSize() }
    }

    override fun display(indent: String) {
        println("$indent $name/ (${getSize()} bytes)")
        children.forEach { child ->
            child.display("$indent  ")
        }
    }
}

// 사용
fun main() {
    println("=== File System Example ===\n")

    // 파일 생성
    val file1 = File("document.txt", 1500)
    val file2 = File("image.png", 2500)
    val file3 = File("video.mp4", 5000)

    // 디렉토리 생성 및 파일 추가
    val documents = Directory("Documents")
    documents.add(file1)

    val pictures = Directory("Pictures")
    pictures.add(file2)

    val videos = Directory("Videos")
    videos.add(file3)

    // 루트 디렉토리
    val root = Directory("root")
    root.add(documents)
    root.add(pictures)
    root.add(videos)
    root.add(File("readme.txt", 500))

    // 전체 구조 출력
    root.display()

    println("\n=== Total Size ===")
    println("Total: ${root.getSize()} bytes")

    // 중첩 디렉토리
    println("\n=== Nested Structure ===")
    val project = Directory("Project")
    val src = Directory("src")
    val test = Directory("test")

    src.add(File("Main.kt", 800))
    src.add(File("Utils.kt", 600))
    test.add(File("MainTest.kt", 400))

    project.add(src)
    project.add(test)
    project.add(File("build.gradle", 200))

    project.display()
}
