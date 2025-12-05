package com.tecsup.productmanager.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _user = MutableStateFlow(auth.currentUser)
    val user: StateFlow<com.google.firebase.auth.FirebaseUser?> = _user

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { t ->
                if (t.isSuccessful) {
                    _user.value = auth.currentUser
                    onResult(true, null)
                } else {
                    onResult(false, t.exception?.localizedMessage)
                }
            }
    }

    fun register(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { t ->
                if (t.isSuccessful) {
                    _user.value = auth.currentUser
                    onResult(true, null)
                } else {
                    onResult(false, t.exception?.localizedMessage)
                }
            }
    }

    fun logout() {
        auth.signOut()
        _user.value = null
    }
}
