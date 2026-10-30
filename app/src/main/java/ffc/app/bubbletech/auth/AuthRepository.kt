package ffc.app.bubbletech.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import ffc.app.bubbletech.R
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    suspend fun register(name: String, email: String, password: String) {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val request = UserProfileChangeRequest.Builder().setDisplayName(name).build()
        result.user?.updateProfile(request)?.await()
        result.user?.getIdToken(true)?.await()
    }

    suspend fun login(email: String, password: String) {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        result.user?.getIdToken(true)?.await()
    }

    suspend fun loginWithGoogle(context: Context) {
        val option = GetSignInWithGoogleOption.Builder(
            context.getString(R.string.default_web_client_id)
        ).build()
        val request = GetCredentialRequest.Builder().addCredentialOption(option).build()
        val response = CredentialManager.create(context).getCredential(context, request)
        val credential = response.credential

        if (credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            val googleToken = GoogleIdTokenCredential.createFrom(credential.data).idToken
            auth.signInWithCredential(GoogleAuthProvider.getCredential(googleToken, null)).await()
        } else {
            throw IllegalStateException("Credencial do Google inválida")
        }
    }

    suspend fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }
}