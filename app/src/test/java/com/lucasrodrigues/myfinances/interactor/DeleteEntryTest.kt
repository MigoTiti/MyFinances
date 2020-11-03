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
class DeleteEntryTest {
    @RelaxedMockK
    lateinit var financialEntryRepository: FinancialEntryRepository

    lateinit var useCase: DeleteEntry

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = DeleteEntry(financialEntryRepository)
    }

    @Test
    fun `Should call repositories`() = runBlockingTest {
        useCase(DeleteEntry.Params(FinancialEntry.Revenue()))

        coVerify {
            financialEntryRepository.deleteEntry(any())
        }
    }
}