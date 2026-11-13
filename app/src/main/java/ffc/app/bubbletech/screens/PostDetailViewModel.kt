package ffc.app.bubbletech.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import ffc.app.bubbletech.data.NewsRepository
import ffc.app.bubbletech.model.Comment
import ffc.app.bubbletech.model.NewsArticle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PostDetailUiState(
    val article: NewsArticle? = null,
    val comments: List<Comment> = emptyList(),
    val liked: Boolean = false,
    val loading: Boolean = true,
    val error: String? = null,
    val commentError: String? = null
)

class PostDetailViewModel(
    private val repository: NewsRepository = NewsRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(PostDetailUiState())
    val state: StateFlow<PostDetailUiState> = _state.asStateFlow()

    private var newsId: String? = null

    fun load(id: String) {
        if (newsId == id && _state.value.article != null) return
        newsId = id

        viewModelScope.launch {
            _state.value = PostDetailUiState()
            try {
                val article = repository.getNewsById(id)
                val comments = repository.getComments(id).items
                _state.value = PostDetailUiState(
                    article = article,
                    comments = comments,
                    liked = NewsRepository.isNewsLiked(id),
                    loading = false
                )
            } catch (e: Exception) {
                _state.value = PostDetailUiState(
                    loading = false,
                    error = "Não foi possível carregar esta notícia"
                )
            }
        }
    }

    fun toggleLike() {
        val id = newsId ?: return
        viewModelScope.launch {
            try {
                val response = if (_state.value.liked) {
                    repository.unlikeNews(id)
                } else {
                    repository.likeNews(id)
                }
                _state.value = _state.value.copy(
                    liked = response.liked,
                    article = _state.value.article?.copy(likesCount = response.likesCount)
                )
                NewsRepository.setNewsLiked(id, response.liked)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Não foi possível atualizar a curtida")
            }
        }
    }

    fun addComment(text: String) {
        val id = newsId ?: return
        val trimmedText = text.trim()
        if (trimmedText.isEmpty() || trimmedText.length > 1000) {
            _state.value = _state.value.copy(
                commentError = "O comentário deve ter entre 1 e 1000 caracteres"
            )
            return
        }

        viewModelScope.launch {
            try {
                val comment = repository.createComment(id, trimmedText)
                _state.value = _state.value.copy(
                    comments = listOf(comment) + _state.value.comments,
                    article = _state.value.article?.copy(
                        commentsCount = _state.value.article!!.commentsCount + 1
                    ),
                    commentError = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(commentError = "Não foi possível enviar o comentário")
            }
        }
    }

    fun editComment(commentId: String, text: String) {
        val id = newsId ?: return
        val trimmedText = text.trim()
        if (trimmedText.isEmpty() || trimmedText.length > 1000) return

        viewModelScope.launch {
            try {
                val comment = repository.updateComment(id, commentId, trimmedText)
                _state.value = _state.value.copy(
                    comments = _state.value.comments.map {
                        if (it.id == comment.id) comment else it
                    }
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(commentError = "Não foi possível editar o comentário")
            }
        }
    }

    fun deleteComment(commentId: String) {
        val id = newsId ?: return
        viewModelScope.launch {
            try {
                repository.deleteComment(id, commentId)
                _state.value = _state.value.copy(
                    comments = _state.value.comments.filterNot { it.id == commentId },
                    article = _state.value.article?.copy(
                        commentsCount = (_state.value.article!!.commentsCount - 1).coerceAtLeast(0)
                    )
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(commentError = "Não foi possível excluir o comentário")
            }
        }
    }

    fun isCurrentUser(comment: Comment): Boolean =
        comment.uid == FirebaseAuth.getInstance().currentUser?.uid
}
