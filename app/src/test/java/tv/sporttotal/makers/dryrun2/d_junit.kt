package tv.sporttotal.makers.dryrun2

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import tv.sporttotal.makers.Match
import tv.sporttotal.makers.Sponsor
import tv.sporttotal.makers.Tournament

// TODO th activity sample was better
class junit {

    val tested = Tournament()
    val normalsponsor = mock<Sponsor>()
    val presmiumSponsor = mock<Sponsor> {
        on { isPremium } doReturn true
    }

    @Test
    fun `should have no impressions without visitors`() {
        tested.showBannersOfSponsor(normalsponsor)

        verify(normalsponsor).addImpressions(0)
    }

    @Nested
    inner class `Given Germany vs France` {

        init {
            tested.add(Match("Germany", "France"))
        }

        @Nested
        inner class `And non premium sponsor` {

            val sponsor = normalsponsor

            @Test
            fun `should get default impressions`() {

                tested.showBannersOfSponsor(sponsor)

                verify(sponsor).addImpressions(100)
            }
        }

        @Nested
        inner class `And  premium sponsor` {
            val sponsor = presmiumSponsor

            @Test
            fun `premium sponsor gets double impressions`() {

                tested.showBannersOfSponsor(sponsor)

                verify(sponsor).addImpressions(200)
            }
        }
    }
}