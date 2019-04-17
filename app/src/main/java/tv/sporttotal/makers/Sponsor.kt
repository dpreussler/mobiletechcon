package tv.sporttotal.makers

@Suppress("unused")
interface Sponsor {

    var isPremium: Boolean

    fun addImpressions(count: Int)

    fun getMoney() : Money
}