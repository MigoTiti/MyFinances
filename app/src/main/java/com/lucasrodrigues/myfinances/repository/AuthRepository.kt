package com.lucasrodrigues.myfinances.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.lucasrodrigues.myfinances.model.User
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepository(
    private val firebaseAuth: FirebaseAuth
) {

    fun hasLoggedUser(): Boolean {
        return getLoggedUser() != null
    }

    fun getLoggedUser(): User? {
        val user = firebaseAuth.currentUser

        return if (user != null)
            User(
                id = user.uid,
                email = user.email ?: "",
                name = user.displayName ?: ""
            )
        else
            null
    }

    suspend fun register(email: String, password: String, name: String) {
        suspendCoroutine<Unit> { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email.toLowerCase(), password)
                .continueWith {
                    firebaseAuth.currentUser!!.updateProfile(
                        UserProfileChangeRequest
                            .Builder()
                            .setDisplayName(name)
                            .build()
                    )
                }
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(Unit)
                    } else {
                        continuation.resumeWithException(task.exception ?: Exception())
                    }
                }
        }
    }

    suspend fun login(email: String, password: String) {
        suspendCoroutine<Unit> { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email.toLowerCase(), password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(Unit)
                    } else {
                        continuation.resumeWithException(task.exception ?: Exception())
                    }
                }
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}