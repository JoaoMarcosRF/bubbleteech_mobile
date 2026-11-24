package ffc.app.bubbletech.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import ffc.app.bubbletech.components.NewsList
import ffc.app.bubbletech.model.NewsArticle

@Composable
fun RecentsScreen(
    articles: List<NewsArticle>,
    contentPadding: PaddingValues,
    onPostClick: (NewsArticle) -> Unit,
    isSaved: (NewsArticle) -> Boolean,
    onToggleSave: (NewsArticle) -> Unit
) {
    NewsList(
        articles = articles,
        contentPadding = contentPadding,
        emptyMessage = "As notícias que você abrir vão aparecer aqui.",
        onPostClick = onPostClick,
        isSaved = isSaved,
        onToggleSave = onToggleSave
    )
}
