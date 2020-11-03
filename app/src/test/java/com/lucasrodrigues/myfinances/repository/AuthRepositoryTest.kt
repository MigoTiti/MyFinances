package com.lucasrodrigues.myfinances.repository

import com.google.firebase.auth.FirebaseAuth
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class AuthRepositoryTest {

    @RelaxedMockK
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var authRepository: AuthRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        authRepository = AuthRepository(firebaseAuth)
    }

    @Nested
    @DisplayName("Has logged user")
    inner class HasLoggedUser {
        @Test
        fun `Should call getLoggedUser`() {
            authRepository.hasLoggedUser()

            verify { authRepository.getLoggedUser() }
        }

        @Test
        fun `Should return false when no logged user`() {
            every { firebaseAuth.currentUser } returns null

            val result = authRepository.hasLoggedUser()

            assert(!result)
        }
    }
}