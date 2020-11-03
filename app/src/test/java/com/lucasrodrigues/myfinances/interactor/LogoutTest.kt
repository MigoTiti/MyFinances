package com.lucasrodrigues.myfinances.interactor

import com.lucasrodrigues.myfinances.repository.AuthRepository
import com.lucasrodrigues.myfinances.repository.FinancialEntryRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class LogoutTest {
    @RelaxedMockK
    lateinit var authRepository: AuthRepository
    @RelaxedMockK
    lateinit var financialEntryRepository: FinancialEntryRepository

    lateinit var useCase: Logout

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = Logout(authRepository, financialEntryRepository)
    }

    @Test
    fun `Should call repositories`() = runBlockingTest {
        useCase()

        coVerify {
            authRepository.logout()
            financialEntryRepository.reset()
        }
    }
}