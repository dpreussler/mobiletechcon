package tv.sporttotal.makers

import org.amshove.kluent.`should be`
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class C_DSL {

    @Test
    fun test() {

        // TODO build sth nicer

        playing { Team("Germany") vs Team("France") }.`is highlight`() `should be` true
    }

    infix fun Team.vs(team: Team) = Match(this, team)

    fun playing(function: () -> Match)  = function

    fun (() -> Match).`is highlight`() = this.invoke().isHightlight()


    @Test
    fun complex() {

//        val mockHttp = (mockHttpLayer()
//                `on GET of` "http://some_url" `it returns` resource("server/1.6/PrivacyPolicy.json")
//                )
//
//
//        inner class UrlResponseBuilder(val outer: MockHttp, val urlKey: String) {
//            infix fun `it returns cache entry`(response: String): MockHttp {
//                whenever(mockCache.get(urlKey)) `it returns` entryContaining(response)
//                return outer
//            }
//
//            infix fun `it returns cache entry`(response: Any): MockHttp {
//                whenever(mockCache.get(urlKey)) `it returns` entryContaining(response)
//                return outer
//            }
//
//            infix fun `it returns`(response: String): MockHttp {
//                whenever(mockNetwork.get(urlKey)) `it returns` entryContaining(response)
//                return outer
//            }
//
//            infix fun `it returns`(response: Any): MockHttp {
//                whenever(mockNetwork.get(urlKey)) `it returns` entryContaining(response)
//                return outer
//            }
//
//        }
    }
}