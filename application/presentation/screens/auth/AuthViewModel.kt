package application.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val authenticatedUser: FirebaseUser? = null,
    val registrationSuccess: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState(authenticatedUser = firebaseAuth.currentUser))
    val uiState: StateFlow<AuthUiState> = _uiState

    fun loginUser(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            // Ensure isLoading is false if we return early
            _uiState.value = _uiState.value.copy(error = "Email and password cannot be empty.", isLoading = false, authenticatedUser = null)
            return
        }
        // Set loading true, clear previous errors/user
        _uiState.value = _uiState.value.copy(isLoading = true, error = null, authenticatedUser = null)
        viewModelScope.launch {
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                _uiState.value = _uiState.value.copy(isLoading = false, authenticatedUser = result.user, error = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message ?: "Login failed.", authenticatedUser = null)
            }
        }
    }

    fun registerUser(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            // Ensure isLoading is false
            _uiState.value = _uiState.value.copy(error = "Email and password cannot be empty.", isLoading = false, authenticatedUser = null, registrationSuccess = false)
            return
        }
        // Set loading true, clear previous errors/user
        _uiState.value = _uiState.value.copy(isLoading = true, error = null, authenticatedUser = null, registrationSuccess = false)
        viewModelScope.launch {
            try {
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                _uiState.value = _uiState.value.copy(isLoading = false, authenticatedUser = result.user, registrationSuccess = true, error = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message ?: "Registration failed.", authenticatedUser = null, registrationSuccess = false)
            }
        }
    }

    fun logoutUser() {
        _uiState.value = _uiState.value.copy(isLoading = true) // Optional: show loading for logout
        firebaseAuth.signOut()
        // State update will trigger LaunchedEffect in StoreHomeScreen for navigation
        _uiState.value = AuthUiState(isLoading = false, authenticatedUser = null, error = null, registrationSuccess = false)
    }

    fun errorShown() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun registrationHandled() {
        _uiState.value = _uiState.value.copy(registrationSuccess = false)
    }
}
