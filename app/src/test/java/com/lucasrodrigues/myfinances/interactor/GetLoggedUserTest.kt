package com.lucasrodrigues.myfinances.interactor

import com.lucasrodrigues.myfinances.repository.AuthRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetLoggedUserTest {
    @RelaxedMockK
    lateinit var authRepository: AuthRepository
    lateinit var useCase: GetLoggedUser

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetLoggedUser(authRepository)
    }

    @Test
    fun `Should call repository`() = runBlockingTest {
        useCase()

        coVerify {
            authRepository.getLoggedUser()
        }
    }
}