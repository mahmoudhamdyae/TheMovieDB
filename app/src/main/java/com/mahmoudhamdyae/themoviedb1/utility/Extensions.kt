package com.mahmoudhamdyae.themoviedb1.utility

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.mahmoudhamdyae.themoviedb1.R

fun launchSignInFlow(signInLauncher: ActivityResultLauncher<Intent>) {
    // Choose authentication providers
    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    // Create and launch sign-in intent
    val signInIntent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .setIsSmartLockEnabled(false)
        .setLogo(R.drawable.ic_launcher_foreground)
        .build()
    signInLauncher.launch(signInIntent)
}

fun Fragment.signOut(signInLauncher: ActivityResultLauncher<Intent>) {
    AuthUI.getInstance()
        .signOut(requireContext())
        .addOnCompleteListener {
            launchSignInFlow(signInLauncher)
        }
}