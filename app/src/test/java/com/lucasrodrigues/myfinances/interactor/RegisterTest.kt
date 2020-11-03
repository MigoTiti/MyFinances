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
class RegisterTest {
    @RelaxedMockK
    lateinit var authRepository: AuthRepository
    lateinit var useCase: Register

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = Register(authRepository)
    }

    @Test
    fun `Should call repository`() = runBlockingTest {
        useCase(Register.Params("", "", ""))

        coVerify {
            authRepository.register(any(), any(), any())
        }
    }

    @Test
    fun `Should pass parameters correctly`() = runBlockingTest {
        useCase(Register.Params("teste", "teste2", "teste3"))

        coVerify {
            authRepository.register("teste", "teste2", "teste3")
        }
    }
}