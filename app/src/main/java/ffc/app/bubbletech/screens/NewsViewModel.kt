package ffc.app.bubbletech.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ffc.app.bubbletech.data.NewsRepository
import ffc.app.bubbletech.model.NewsArticle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class NewsListUiState(
    val items: List<NewsArticle> = emptyList(),
    val nextCursor: String? = null,
    val loading: Boolean = false,
    val error: String? = null
)

class NewsViewModel(
    private val repository: NewsRepository = NewsRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(NewsListUiState())
    val state: StateFlow<NewsListUiState> = _state.asStateFlow()

    private var category: String? = null
    private var search: String? = null

    fun loadNews(category: String? = this.category, search: String? = this.search) {
        this.category = category
        this.search = search
        if (_state.value.loading) return

        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                val response = repository.getNews(category, search, null)
                _state.value = NewsListUiState(
                    items = response.items,
                    nextCursor = response.nextCursor
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = "Não foi possível carregar as notícias"
                )
            }
        }
    }

    fun loadNextPage() {
        val cursor = _state.value.nextCursor ?: return
        if (_state.value.loading) return

        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                val response = repository.getNews(category, search, cursor)
                _state.value = _state.value.copy(
                    items = _state.value.items + response.items,
                    nextCursor = response.nextCursor,
                    loading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = "Não foi possível carregar mais notícias"
                )
            }
        }
    }
}
