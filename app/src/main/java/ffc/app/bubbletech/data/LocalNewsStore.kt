package ffc.app.bubbletech.data

import androidx.compose.runtime.mutableStateListOf
import ffc.app.bubbletech.model.NewsArticle

object LocalNewsStore {

    private const val MAX_RECENTS = 30

    val saved = mutableStateListOf<NewsArticle>()
    val recents = mutableStateListOf<NewsArticle>()

    fun isSaved(article: NewsArticle): Boolean = saved.any { it.id == article.id }

    fun toggleSaved(article: NewsArticle) {
        if (!saved.removeAll { it.id == article.id }) {
            saved.add(0, article)
        }
    }

    fun clear() {
        saved.clear()
        recents.clear()
    }

    fun addRecent(article: NewsArticle) {
        recents.removeAll { it.id == article.id }
        recents.add(0, article)
        if (recents.size > MAX_RECENTS) {
            recents.removeAt(recents.lastIndex)
        }
    }
}
