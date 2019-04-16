package tv.sporttotal.makers

import java.util.Currency

sealed class Money(open val amount: Long, val currency: Currency) {

    data class Euro(override val amount: Long):
        Money(amount, Currency.getInstance("EUR"))
}
