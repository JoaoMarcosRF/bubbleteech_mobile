package ffc.app.bubbletech.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import ffc.app.bubbletech.model.Post
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ffc.app.bubbletech.components.PostCard
import ffc.app.bubbletech.data.FakeRepository
import ffc.app.bubbletech.ui.theme.BackgroundBlue
import ffc.app.bubbletech.ui.theme.BubbleTechTheme

@Composable
fun HomeScreen(posts: List<Post>) {
    Scaffold(
        containerColor = BackgroundBlue
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        ) {
            items(posts) { post ->
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