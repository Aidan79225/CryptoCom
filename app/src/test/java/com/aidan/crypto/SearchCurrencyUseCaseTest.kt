package com.aidan.crypto

import com.aidan.crypto.domain.SearchCurrencyInfoUseCase
import com.aidan.crypto.entity.CurrencyInfo
import org.junit.Test
import org.junit.Assert.*

class SearchCurrencyUseCaseTest {
    private val searchCurrencyInfoUseCase = SearchCurrencyInfoUseCase()

    @Test
    fun `verify name starts with the search term - 1`() {
        val testData = listOf(
            CurrencyInfo(
                "1",
                "Foobar",
                "",
                ""
            ),
            CurrencyInfo(
                "2",
                "Barfoo",
                "",
                ""
            )
        )
        assertEquals(listOf(testData[0]), searchCurrencyInfoUseCase.execute("foo", testData))
    }

    @Test
    fun `verify name starts with the search term - 2`() {
        val testData = listOf(
            CurrencyInfo(
                "1",
                "Ethereum",
                "",
                ""
            ),
            CurrencyInfo(
                "2",
                "Ethereum Classic",
                "",
                ""
            )
        )
        assertEquals(testData, searchCurrencyInfoUseCase.execute("Ethereum", testData))
    }

    @Test
    fun `verify partial matches`() {
        val testData = listOf(
            CurrencyInfo(
                "1",
                "Ethereum Classic",
                "",
                ""
            ),
            CurrencyInfo(
                "2",
                "Tronclassic",
                "",
                ""
            )
        )

        assertEquals(listOf(testData[0]), searchCurrencyInfoUseCase.execute("Classic", testData))
    }

    @Test
    fun `verify symbol starts with the search term`() {
        val testData = listOf(
            CurrencyInfo(
                "1",
                "",
                "ETH",
                ""
            ),
            CurrencyInfo(
                "2",
                "",
                "ETC",
                ""
            ),
            CurrencyInfo(
                "3",
                "",
                "ETN",
                ""
            ),
            CurrencyInfo(
                "4",
                "",
                "BET",
                ""
            )
        )

        assertEquals(
            listOf(
                testData[0],
                testData[1],
                testData[2]
            ),
            searchCurrencyInfoUseCase.execute("ET", testData)
        )
    }
}