package ffc.app.bubbletech.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import ffc.app.bubbletech.model.Post
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ffc.app.bubbletech.components.BottomNavigationBar
import ffc.app.bubbletech.components.CategoryChips
import ffc.app.bubbletech.components.PostCard
import ffc.app.bubbletech.data.FakeRepository
import ffc.app.bubbletech.ui.theme.BackgroundBlue
import ffc.app.bubbletech.ui.theme.BubbleTechTheme

@Composable
fun HomeScreen(posts: List<Post>) {
    var selectedCategory by remember { mutableStateOf("Hot") }
    var selectedTab by remember { mutableStateOf("Home") }

    val visiblePosts = if (selectedCategory == "Hot") {
        posts
    } else {
        posts.filter { post -> post.category == selectedCategory }
    }

    Scaffold(
        containerColor = BackgroundBlue,
        topBar = {
            CategoryChips(
                categories = listOf("Hot", "IA", "Games", "Desenvolvimento", "Mercado"),
                selected = selectedCategory,
                onSelect = { category -> selectedCategory = category }
            )
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
            items(visiblePosts) { post ->
                PostCard(post = post)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00174E, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    BubbleTechTheme {
        HomeScreen(posts = FakeRepository().getPosts())
    }
}