package application.presentation.screens.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import application.data.remote.api.FakeStoreApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoriesUiState(
    val isLoading: Boolean = false,
    val categories: List<String> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val fakeStoreApiService: FakeStoreApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState

    init {
        fetchCategories()
    }

    fun fetchCategories() {
        _uiState.value = CategoriesUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val categories = fakeStoreApiService.getCategories()
                _uiState.value = CategoriesUiState(categories = categories)
            } catch (e: Exception) {
                _uiState.value = CategoriesUiState(error = e.message ?: "Failed to fetch categories.")
            }
        }
    }

    fun errorShown() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
