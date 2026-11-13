package ffc.app.bubbletech.auth

import android.content.Context
import android.util.Log
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import ffc.app.bubbletech.network.ApiClient
import ffc.app.bubbletech.network.MeResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

data class AuthUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val message: String? = null,
    val profile: MeResponse? = null
)

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _state.value = AuthUiState(error = "Preencha e-mail e senha")
            return
        }
        submit { repository.login(email.trim(), password) }
    }

    fun register(name: String, email: String, password: String, confirmPassword: String) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _state.value = AuthUiState(error = "Preencha todos os campos")
            return
        }
        if (password != confirmPassword) {
            _state.value = AuthUiState(error = "As senhas não coincidem")
            return
        }
        submit { repository.register(name.trim(), email.trim(), password) }
    }

    fun loginWithGoogle(context: Context) {
        submit { repository.loginWithGoogle(context) }
    }

    fun resetPassword(email: String) {
        if (email.isBlank()) {
            _state.value = AuthUiState(error = "Digite seu e-mail primeiro")
            return
        }
        viewModelScope.launch {
            try {
                repository.resetPassword(email.trim())
                _state.value = AuthUiState(message = "Enviamos um e-mail para redefinir a senha")
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = AuthUiState(error = friendlyMessage(e))
            }
        }
    }

    private fun submit(action: suspend () -> Unit) {
        viewModelScope.launch {
            _state.value = AuthUiState(loading = true)
            try {
                action()
                val profile = ApiClient.api.me()
                _state.value = AuthUiState(profile = profile)
            } catch (e: CancellationException) {
                throw e
            } catch (e: GetCredentialCancellationException) {
                _state.value = AuthUiState()
            } catch (e: Exception) {
                _state.value = AuthUiState(error = friendlyMessage(e))
                Log.e("AuthViewModel", "falha no fluxo", e)
            }
        }
    }

    private fun friendlyMessage(e: Exception): String = when (e) {
        is FirebaseAuthWeakPasswordException -> "A senha precisa ter pelo menos 6 caracteres"
        is FirebaseAuthUserCollisionException -> "Este e-mail já está cadastrado"
        is FirebaseAuthInvalidCredentialsException -> "E-mail ou senha incorretos"
        is FirebaseAuthInvalidUserException -> "E-mail ou senha incorretos"
        is FirebaseNetworkException -> "Sem conexão com a internet"
        is HttpException -> "A API recusou o acesso (${e.code()})"
        is IOException -> "Sem conexão com o servidor ${e.message}"
        else -> "Algo deu errado, tente de novo"
    }
}