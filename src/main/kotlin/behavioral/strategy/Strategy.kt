package org.example.behavioral.strategy

/**
 * 알고리즘 군을 정의하고 각각을 캡슐화하여 교환 가능하게 만드는 행위 패턴
 * - 클라이언트와 독립적으로 알고리즘 변경 가능
 *
 * 인터페이스의 구현체를 여러 개 두고
 * 인터페이스를 주입하는 로직을 작성해 거기서 구현체를 셋팅한다.
 *
 * 사용하는 경우
 * - 같은 문제를 해결하는 여러 알고리즘이 있을 때
 * - 런타임에 알고리즘을 선택하고 싶을 때
 * - 조건문이 많아질 때
 * - 알고리즘을 독립적으로 변경하고 싶을 때
 * - 클라이언트가 구현 세부사항을 몰라야 할 때
 *
 * 사용을 피하는 경우
 * - 알고리즘이 하나 또는 두 개 뿐일 때
 * - 알고리즘이 거의 변경되지 않을 때
 */

/**
 * 예제 코드 : 결제 시스템
 */

interface PaymentStrategy {
    fun pay(amount: Double): Boolean
    fun getPaymentDetails(): String
}

// ConcreteStrategy - 신용카드
class CreditCardStrategy(
    private val cardNumber: String,
    private val cvv: String,
    private val expiryDate: String
) : PaymentStrategy {

    override fun pay(amount: Double): Boolean {
        println("\nProcessing Credit Card Payment")
        println("Card: **** **** **** ${cardNumber.takeLast(4)}")
        println("Amount: $${"%.2f".format(amount)}")

        // 실제로는 카드 검증 및 결제 처리
        return if (validateCard()) {
            println("Payment successful!")
            true
        } else {
            println("Payment failed!")
            false
        }
    }

    override fun getPaymentDetails() = "Credit Card ending in ${cardNumber.takeLast(4)}"

    private fun validateCard(): Boolean {
        // 카드 검증 로직
        return cardNumber.length == 16 && cvv.length == 3
    }
}

// ConcreteStrategy - PayPal
class PayPalStrategy(
    private val email: String,
    private val password: String
) : PaymentStrategy {

    override fun pay(amount: Double): Boolean {
        println("\nProcessing PayPal Payment")
        println("   Account: $email")
        println("   Amount: $${"%.2f".format(amount)}")

        return if (authenticate()) {
            println("Payment successful!")
            true
        } else {
            println("Authentication failed!")
            false
        }
    }

    override fun getPaymentDetails() = "PayPal account: $email"

    private fun authenticate(): Boolean {
        // PayPal 인증 로직
        return email.contains("@") && password.length >= 6
    }
}

// ConcreteStrategy - 암호화폐
class CryptoStrategy(
    private val walletAddress: String,
    private val cryptoType: String
) : PaymentStrategy {

    override fun pay(amount: Double): Boolean {
        println("\n₿ Processing Cryptocurrency Payment")
        println("   Type: $cryptoType")
        println("   Wallet: ${walletAddress.take(10)}...${walletAddress.takeLast(10)}")
        println("   Amount: $${"%.2f".format(amount)}")

        return if (verifyWallet()) {
            println("Transaction pending... (blockchain confirmation)")
            println("Payment initiated!")
            true
        } else {
            println("Invalid wallet address!")
            false
        }
    }

    override fun getPaymentDetails() = "$cryptoType wallet: ${walletAddress.take(10)}..."

    private fun verifyWallet(): Boolean {
        return walletAddress.length >= 26
    }
}

// ConcreteStrategy - 계좌 이체
class BankTransferStrategy(
    private val accountNumber: String,
    private val bankName: String
) : PaymentStrategy {

    override fun pay(amount: Double): Boolean {
        println("\nProcessing Bank Transfer")
        println("Bank: $bankName")
        println("Account: **** ${accountNumber.takeLast(4)}")
        println("Amount: $${"%.2f".format(amount)}")
        println("Processing... (may take 1-3 business days)")
        println("Transfer initiated!")
        return true
    }

    override fun getPaymentDetails() = "$bankName account ending in ${accountNumber.takeLast(4)}"
}

// Context - 쇼핑 카트
class ShoppingCart {
    private val items = mutableListOf<CartItem>()
    private var paymentStrategy: PaymentStrategy? = null

    fun addItem(name: String, price: Double) {
        items.add(CartItem(name, price))
        println("Added: $name - $${"%.2f".format(price)}")
    }

    fun setPaymentMethod(strategy: PaymentStrategy) {
        this.paymentStrategy = strategy
        println("\nPayment method set: ${strategy.getPaymentDetails()}")
    }

    fun checkout(): Boolean {
        val total = items.sumOf { it.price }

        println("\n" + "=".repeat(50))
        println("CHECKOUT")
        println("=".repeat(50))

        items.forEach { item ->
            println("   ${item.name}: $${"%.2f".format(item.price)}")
        }

        println("   " + "-".repeat(46))
        println("   Total: $${"%.2f".format(total)}")
        println("=".repeat(50))

        val strategy = paymentStrategy
        if (strategy == null) {
            println("No payment method selected!")
            return false
        }

        return strategy.pay(total)
    }
}

data class CartItem(val name: String, val price: Double)

// 사용
fun main() {
    println("=== Strategy Pattern - Payment System ===\n")

    val cart = ShoppingCart()

    // 장바구니에 상품 추가
    cart.addItem("Laptop", 999.99)
    cart.addItem("Mouse", 29.99)
    cart.addItem("Keyboard", 79.99)

    // 신용카드로 결제
    println("\n--- Paying with Credit Card ---")
    cart.setPaymentMethod(
        CreditCardStrategy("1234567890123456", "123", "12/25")
    )
    cart.checkout()

    println("\n" + "=".repeat(60) + "\n")

    // 새로운 장바구니
    val cart2 = ShoppingCart()
    cart2.addItem("Book", 24.99)
    cart2.addItem("Coffee Mug", 12.99)

    // PayPal로 결제
    println("\n--- Paying with PayPal ---")
    cart2.setPaymentMethod(
        PayPalStrategy("user@example.com", "securePass123")
    )
    cart2.checkout()

    println("\n" + "=".repeat(60) + "\n")

    // 암호화폐로 결제
    val cart3 = ShoppingCart()
    cart3.addItem("Gaming Console", 499.99)

    println("\n--- Paying with Cryptocurrency ---")
    cart3.setPaymentMethod(
        CryptoStrategy("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", "Bitcoin")
    )
    cart3.checkout()
}
