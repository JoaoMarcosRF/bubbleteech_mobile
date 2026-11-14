package ffc.app.bubbletech.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import ffc.app.bubbletech.components.BottomNavigationBar
import ffc.app.bubbletech.components.CategoryChips
import ffc.app.bubbletech.components.TopSearchBar
import ffc.app.bubbletech.model.NewsArticle
import ffc.app.bubbletech.ui.theme.Background
import ffc.app.bubbletech.ui.theme.BubbleTechTheme

@Composable
fun HomeScreen(
    onPostClick: (NewsArticle) -> Unit = {},
    newsViewModel: NewsViewModel = viewModel()
) {
    val state by newsViewModel.state.collectAsStateWithLifecycle()
    var selectedCategory by remember { mutableStateOf("Hot") }
    var selectedTab by remember { mutableStateOf("Feed") }
    var searchQuery by remember { mutableStateOf("") }

    val currentUser = remember { FirebaseAuth.getInstance().currentUser }
    val userInitial = remember(currentUser) {
        currentUser?.displayName?.take(1)?.uppercase()
            ?: currentUser?.email?.take(1)?.uppercase()
            ?: "E"
    }

    LaunchedEffect(selectedCategory) {
        newsViewModel.loadNews(
            category = selectedCategory
                .takeUnless { it == "Hot" }
                ?.lowercase()
        )
    }

    Scaffold(
        containerColor = Background,
        topBar = {
            Column {
                TopSearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    userInitial = userInitial
                )
                CategoryChips(
                    categories = listOf("Hot", "IA", "Games", "Desenvolvimento", "Mercado"),
                    selected = selectedCategory,
                    onSelect = { category -> selectedCategory = category }
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                selected = selectedTab,
                onSelect = { tab -> selectedTab = tab }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        ) {
//            itemsIndexed(state.items) { index, article ->
//                PostCard(
//                    post = article,
//                    onClick = { onPostClick(article) }
//                )
//                if (index == state.items.lastIndex && state.nextCursor != null) {
//                    LaunchedEffect(state.nextCursor) {
//                        newsViewModel.loadNextPage()
//                    }
//                }
//            }
//            if (state.loading) {
//                item {
//                    CircularProgressIndicator()
//                }
//            }
//            state.error?.let { error ->
//                item {
//                    Text(error)
//                }
//            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00174E, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    BubbleTechTheme {
        HomeScreen()
    }
}
