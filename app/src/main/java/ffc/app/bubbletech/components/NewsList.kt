package ffc.app.bubbletech.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ffc.app.bubbletech.model.NewsArticle
import ffc.app.bubbletech.ui.theme.GrayText

@Composable
fun NewsList(
    articles: List<NewsArticle>,
    contentPadding: PaddingValues,
    emptyMessage: String,
    onPostClick: (NewsArticle) -> Unit,
    isSaved: (NewsArticle) -> Boolean,
    onToggleSave: (NewsArticle) -> Unit,
    loading: Boolean = false,
    error: String? = null,
    onLoadMore: (() -> Unit)? = null
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding
    ) {
        itemsIndexed(articles) { index, article ->
            PostCardAlternative(
                post = article,
                onClick = { onPostClick(article) },
                isSaved = isSaved(article),
                onToggleSave = { onToggleSave(article) }
            )
            if (index == articles.lastIndex && onLoadMore != null) {
                LaunchedEffect(articles.size) {
                    onLoadMore()
                }
            }
        }

        if (loading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        if (error != null) {
            item { CenteredMessage(error) }
        }

        if (!loading && error == null && articles.isEmpty()) {
            item { CenteredMessage(emptyMessage) }
        }
    }
}

@Composable
private fun CenteredMessage(text: String) {
    Text(
        text = text,
        color = GrayText,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 48.dp)
    )
}
