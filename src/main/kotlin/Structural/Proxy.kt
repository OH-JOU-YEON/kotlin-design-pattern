package org.example.Structural

/**
 * 구조 디자인 패턴 > 특정 응용 프로그램 시나리오의 문제를 해결하는데 사용
 */

/**
 * 프록시 - 원본 클래스에 연관 없는 기능 추가 시 사용
 *
 * 원본 객체와 클라이언트 사이에 프록시 객체를 끼워 기능을 추가한다.
 * 원본 객체와 프록시 객체는 같은 인터페이스를 구현한다.
 */

/**
 * 예제 코드 - 은행 계좌
 */

data class User(
    val username: String,
    val role: String
)

interface BankAccount {
    fun getBalance(): Double
    fun deposit(amount: Double)
    fun withdraw(amount: Double)
}

class RealBankAccount(
    private val accountNumber: String,
    private var balance: Double = 0.0
) : BankAccount {
    override fun getBalance(): Double {
        println("Real Account: Getting balance")
        return balance
    }

    override fun deposit(amount: Double) {
        println("Real Account: Depositing $amount")
        balance += amount
        println("New balance: %balance")
    }

    override fun withdraw(amount: Double) {
        if (balance >= amount) {
            println("Real Account: Withdrawing $amount")
            balance -= amount
            println("New balance: $balance")
        } else {
            println("Real Account: Insufficient funds")
        }
    }
}

// 권한 검사 프록시
class BankAccountProxy(
    private val accountNumber: String,
    private val owner: User,
    private val currentUser: User
) : BankAccount {
    private val realAccount = RealBankAccount(accountNumber, 1000.0)

    private fun checkAccess(operation: String): Boolean {
        return when (operation) {
            "read" -> {
                // 본인과 관리자만 조회 가능
                currentUser.username == owner.username ||
                        currentUser.role == "ADMIN"
            }

            "write" -> {
                // 입출금은 본인만 가능
                currentUser.username == owner.username
            }

            else -> false
        }
    }

    override fun getBalance(): Double {
        return if (checkAccess("read")) {
            println("🔓 Proxy: Access granted for balance inquiry")
            realAccount.getBalance()
        } else {
            println("🔒 Proxy: Access denied for balance inquiry")
            throw SecurityException("Access denied: You don't have permission to view this account")
        }
    }

    override fun deposit(amount: Double) {
        if (checkAccess("write")) {
            println("🔓 Proxy: Access granted for deposit")
            realAccount.deposit(amount)
        } else {
            println("🔒 Proxy: Access denied for deposit")
            throw SecurityException("Access denied: You don't have permission to deposit")
        }
    }

    override fun withdraw(amount: Double) {
        if (checkAccess("write")) {
            println("🔓 Proxy: Access granted for withdrawal")
            realAccount.withdraw(amount)
        } else {
            println("🔒 Proxy: Access denied for withdrawal")
            throw SecurityException("Access denied: You don't have permission to withdraw")
        }
    }
}

fun main() {
    val accountOwner = User("john", "USER")
    val admin = User("admin", "ADMIN")
    val stranger = User("jane", "USER")

    println("=== Owner accessing own account ===\n")
    val ownerProxy = BankAccountProxy("12345", accountOwner, accountOwner)
    println("Balance: ${ownerProxy.getBalance()}")
    ownerProxy.deposit(500.0)
    ownerProxy.withdraw(200.0)

    println("\n=== Admin accessing user's account ===\n")
    val adminProxy = BankAccountProxy("12345", accountOwner, admin)
    println("Balance: ${adminProxy.getBalance()}") // 조회 가능
    try {
        adminProxy.deposit(100.0) // 입금 불가
    } catch (e: SecurityException) {
        println("Error: ${e.message}")
    }

    println("\n=== Stranger accessing user's account ===\n")
    val strangerProxy = BankAccountProxy("12345", accountOwner, stranger)
    try {
        strangerProxy.getBalance() // 조회 불가
    } catch (e: SecurityException) {
        println("Error: ${e.message}")
    }

}