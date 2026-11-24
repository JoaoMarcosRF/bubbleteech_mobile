package ffc.app.bubbletech.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import ffc.app.bubbletech.components.CategoryChips
import ffc.app.bubbletech.components.FilterField
import ffc.app.bubbletech.components.MainScaffold
import ffc.app.bubbletech.components.NewsList
import ffc.app.bubbletech.data.LocalNewsStore
import ffc.app.bubbletech.model.NewsArticle
import ffc.app.bubbletech.model.NewsCategory
import kotlinx.coroutines.delay

private const val ALL_CATEGORIES = "All"

@Composable
fun HomeScreen(
    onPostClick: (NewsArticle) -> Unit = {},
    onProfileClick: () -> Unit = {},
    newsViewModel: NewsViewModel = viewModel(),
    hotViewModel: NewsViewModel = viewModel(key = "hot")
) {
    val feedState by newsViewModel.state.collectAsStateWithLifecycle()
    val hotState by hotViewModel.state.collectAsStateWithLifecycle()

    var selectedTab by remember { mutableStateOf("Feed") }
    var searchQuery by remember { mutableStateOf("") }
    var feedCategory by remember { mutableStateOf(ALL_CATEGORIES) }
    var hotCategory by remember { mutableStateOf<String?>(null) }
    var savedFilter by remember { mutableStateOf("") }

    val currentUser = remember { FirebaseAuth.getInstance().currentUser }
    val userInitial = remember(currentUser) {
        currentUser?.displayName?.take(1)?.uppercase()
            ?: currentUser?.email?.take(1)?.uppercase()
            ?: "E"
    }

    val openPost: (NewsArticle) -> Unit = { article ->
        LocalNewsStore.addRecent(article)
        onPostClick(article)
    }

    LaunchedEffect(feedCategory, searchQuery) {
        if (searchQuery.isNotBlank()) delay(400)
        newsViewModel.loadNews(
            category = NewsCategory.apiValueOf(feedCategory),
            search = searchQuery.trim().ifBlank { null }
        )
    }

    LaunchedEffect(selectedTab, hotCategory, searchQuery) {
        if (selectedTab != "Hot") return@LaunchedEffect
        if (searchQuery.isNotBlank()) delay(400)
        hotViewModel.loadNews(
            category = hotCategory?.let { NewsCategory.apiValueOf(it) },
            search = searchQuery.trim().ifBlank { null }
        )
    }

    MainScaffold(
        selectedTab = selectedTab,
        onTabSelect = { tab -> selectedTab = tab },
        searchQuery = searchQuery,
        onSearchQueryChange = { searchQuery = it },
        userInitial = userInitial,
        onAvatarClick = onProfileClick,
        topBarExtra = {
            when (selectedTab) {
                "Feed" -> CategoryChips(
                    categories = listOf(ALL_CATEGORIES) + NewsCategory.labels,
                    selected = feedCategory,
                    onSelect = { category -> feedCategory = category }
                )
                "Hot" -> CategoryChips(
                    categories = NewsCategory.labels,
                    selected = hotCategory ?: "",
                    onSelect = { category ->
                        hotCategory = if (hotCategory == category) null else category
                    }
                )
                "Saved" -> FilterField(
                    value = savedFilter,
                    onValueChange = { savedFilter = it }
                )
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            "Hot" -> HotScreen(
                articles = hotState.items,
                contentPadding = innerPadding,
                onPostClick = openPost,
                isSaved = LocalNewsStore::isSaved,
                onToggleSave = LocalNewsStore::toggleSaved,
                loading = hotState.loading,
                error = hotState.error,
                onLoadMore = if (hotState.nextCursor != null) hotViewModel::loadNextPage else null
            )
            "Saved" -> SavedScreen(
                articles = LocalNewsStore.saved,
                filter = savedFilter,
                contentPadding = innerPadding,
                onPostClick = openPost,
                onToggleSave = LocalNewsStore::toggleSaved
            )
            "Recents" -> RecentsScreen(
                articles = LocalNewsStore.recents,
                contentPadding = innerPadding,
                onPostClick = openPost,
                isSaved = LocalNewsStore::isSaved,
                onToggleSave = LocalNewsStore::toggleSaved
            )
            else -> FeedContent(
                articles = feedState.items,
                contentPadding = innerPadding,
                onPostClick = openPost,
                loading = feedState.loading,
                error = feedState.error,
                onLoadMore = if (feedState.nextCursor != null) newsViewModel::loadNextPage else null
            )
        }
    }
}

@Composable
private fun FeedContent(
    articles: List<NewsArticle>,
    contentPadding: PaddingValues,
    onPostClick: (NewsArticle) -> Unit,
    loading: Boolean = false,
    error: String? = null,
    onLoadMore: (() -> Unit)? = null
) {
    NewsList(
        articles = articles,
        contentPadding = contentPadding,
        emptyMessage = "Nenhuma notícia encontrada",
        onPostClick = onPostClick,
        isSaved = LocalNewsStore::isSaved,
        onToggleSave = LocalNewsStore::toggleSaved,
        loading = loading,
        error = error,
        onLoadMore = onLoadMore
    )
}
