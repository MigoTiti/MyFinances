package com.lucasrodrigues.myfinances.interactor

import com.lucasrodrigues.myfinances.model.FinancialEntry
import com.lucasrodrigues.myfinances.repository.FinancialEntryRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class UpdateEntryTest {
    @RelaxedMockK
    lateinit var financialEntryRepository: FinancialEntryRepository

    lateinit var useCase: UpdateEntry

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = UpdateEntry(financialEntryRepository)
    }

    @Test
    fun `Should call repositories`() = runBlockingTest {
        useCase(UpdateEntry.Params(FinancialEntry.Revenue()))

        coVerify {
            financialEntryRepository.updateEntry(any())
        }
    }
}