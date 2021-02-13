package dev.qori.deckiru.model

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth

object FirebaseGoogleAuth {
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code:Int=123
    val firebaseAuth= FirebaseAuth.getInstance()

}