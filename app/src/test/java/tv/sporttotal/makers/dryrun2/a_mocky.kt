package tv.sporttotal.makers.dryrun2

import com.nhaarman.mockitokotlin2.KStubbing
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
import tv.sporttotal.makers.Match
import tv.sporttotal.makers.Sponsor
import tv.sporttotal.makers.Tournament

class mocky {

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

    @Test
    fun `normal sponsor gets default impressions`() {
        tested.add(Match("Germany", "France"))

        tested.showBannersOfSponsor(normalsponsor)

        verify(normalsponsor).addImpressions(100)
    }

    @Test
    fun `premium sponsor gets double impressions`() {
        tested.add(Match("Germany", "France"))

        tested.showBannersOfSponsor(presmiumSponsor)

        verify(presmiumSponsor).addImpressions(200)
    }


    @Test
    fun `custom stubber`() {
        mockPremiumSponsor{
            on { isPremium } doReturn true
        }
    }

    private fun mockPremiumSponsor(function: KStubbing<Sponsor>.() -> Any) =
        mock<Sponsor>() {
            function(this)
        }
}