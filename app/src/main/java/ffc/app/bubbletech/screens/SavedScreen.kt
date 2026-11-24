package ffc.app.bubbletech.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import ffc.app.bubbletech.components.NewsList
import ffc.app.bubbletech.model.NewsArticle

@Composable
fun SavedScreen(
    articles: List<NewsArticle>,
    filter: String,
    contentPadding: PaddingValues,
    onPostClick: (NewsArticle) -> Unit,
    onToggleSave: (NewsArticle) -> Unit
) {
    val visibleArticles = if (filter.isBlank()) {
        articles
    } else {
        articles.filter { article ->
            article.title.contains(filter, ignoreCase = true) ||
                article.source.contains(filter, ignoreCase = true)
        }
    }

    NewsList(
        articles = visibleArticles,
        contentPadding = contentPadding,
        emptyMessage = if (filter.isBlank()) {
            "Você ainda não salvou nenhuma notícia.\nToque no ícone de salvar de um card para guardar aqui."
        } else {
            "Nenhuma notícia salva com \"$filter\""
        },
        onPostClick = onPostClick,
        isSaved = { true },
        onToggleSave = onToggleSave
    )
}
