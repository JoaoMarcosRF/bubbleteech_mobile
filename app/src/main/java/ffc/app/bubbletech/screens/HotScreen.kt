package ffc.app.bubbletech.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ffc.app.bubbletech.components.NewsList
import ffc.app.bubbletech.model.NewsArticle

@Composable
fun HotScreen(
    articles: List<NewsArticle>,
    contentPadding: PaddingValues,
    onPostClick: (NewsArticle) -> Unit,
    isSaved: (NewsArticle) -> Boolean,
    onToggleSave: (NewsArticle) -> Unit,
    loading: Boolean = false,
    error: String? = null,
    onLoadMore: (() -> Unit)? = null
) {
    val hotArticles = remember(articles) {
        articles.sortedByDescending { it.likesCount + it.commentsCount }
    }

    NewsList(
        articles = hotArticles,
        contentPadding = contentPadding,
        emptyMessage = "Nenhuma notícia em alta agora",
        onPostClick = onPostClick,
        isSaved = isSaved,
        onToggleSave = onToggleSave,
        loading = loading,
        error = error,
        onLoadMore = onLoadMore
    )
}
