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
class LoginTest {
    @RelaxedMockK
    lateinit var authRepository: AuthRepository
    lateinit var useCase: Login

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = Login(authRepository)
    }

    @Test
    fun `Should call repository`() = runBlockingTest {
        useCase(Login.Params("", ""))

        coVerify {
            authRepository.login(any(), any())
        }
    }

    @Test
    fun `Should pass parameters correctly`() = runBlockingTest {
        useCase(Login.Params("teste", "teste"))

        coVerify {
            authRepository.login("teste", "teste")
        }
    }
}