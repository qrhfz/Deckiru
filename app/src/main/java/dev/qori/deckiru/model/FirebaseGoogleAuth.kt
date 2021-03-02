package dev.qori.deckiru.model

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dev.qori.deckiru.R

object FirebaseGoogleAuth {

    val Req_Code:Int=123
    val firebaseAuth= FirebaseAuth.getInstance()

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("302198093468-18v9bst6e43jecphnvo6bse7q7e0skgb.apps.googleusercontent.com")
            .requestEmail()
            .build()
}