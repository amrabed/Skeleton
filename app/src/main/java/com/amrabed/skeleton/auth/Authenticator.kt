package com.amrabed.skeleton.auth

import android.content.Intent
import com.amrabed.skeleton.R
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth


object Authenticator {
    private val auth get() = FirebaseAuth.getInstance()
    val user get() = auth.currentUser

    fun createSignInIntent(): Intent {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        return AuthUI.getInstance().createSignInIntentBuilder()
            .enableAnonymousUsersAutoUpgrade()
            .setTheme(R.style.AppTheme_FullScreen)
            .setLogo(R.drawable.app_icon)
            .setAvailableProviders(providers)
            .build()
    }

    fun signOut() {
        auth.signOut()
    }
}