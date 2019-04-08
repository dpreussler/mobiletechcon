package tv.sporttotal.makers

import android.app.Activity
import android.app.onCreate
import android.os.Bundle
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class D_unit5 {

    val tested = Activity()

    @Nested
    inner class `When onCreate is called` {

        @Nested
        inner class `first time` {

            init {
                tested.onCreate(null)
            }

            @Test
            fun `should do something`() {

            }
        }

        @Nested
        inner class `on recreation` {

            init {
                tested.onCreate(Bundle())
            }

            @Test
            fun `should do something else`() {

            }
        }
    }
}

class D_unit5_2 {

    val tested = Activity()

    @Nested
    inner class `When onCreate is called` {

        @Nested
        inner class `first time` {

            init {
                tested.onCreate(null)
            }

            @Nested
            inner class `and then resumed` {

                @TestFactory
                fun `should do something`() = generify
            }

            @TestFactory
            fun `should do something`() = generify
        }

        @Nested
        inner class `on recreation` {

            init {
                tested.onCreate(Bundle())
            }

            @Nested
            inner class `and then resumed` {

                @TestFactory
                fun `should do something`() = generify
            }

            @TestFactory
            fun `should do something`() = generify
        }
    }

    val generify = listOf(
        dynamicTest("should do A") {
            assert(true)
        },
        dynamicTest("should do B") {
            assert(true)
        }
    )
}