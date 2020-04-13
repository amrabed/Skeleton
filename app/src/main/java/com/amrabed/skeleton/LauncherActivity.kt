package com.amrabed.skeleton

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.amrabed.skeleton.auth.Authenticator

class LauncherActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        if (Authenticator.user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }
}