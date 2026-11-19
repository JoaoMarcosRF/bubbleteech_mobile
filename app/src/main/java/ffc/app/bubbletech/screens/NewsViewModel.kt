package ffc.app.bubbletech.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ffc.app.bubbletech.data.NewsRepository
import ffc.app.bubbletech.model.NewsArticle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

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
    private var loadJob: Job? = null

    fun loadNews(category: String?, search: String?) {
        this.category = category
        this.search = search

        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                val response = repository.getNews(category, search, null)
                _state.value = NewsListUiState(
                    items = response.items,
                    nextCursor = response.nextCursor
                )
            } catch (e: CancellationException) {
                throw e
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

        loadJob = viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                val response = repository.getNews(category, search, cursor)
                _state.value = _state.value.copy(
                    items = _state.value.items + response.items,
                    nextCursor = response.nextCursor,
                    loading = false
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = "Não foi possível carregar mais notícias"
                )
            }
        }
    }
}