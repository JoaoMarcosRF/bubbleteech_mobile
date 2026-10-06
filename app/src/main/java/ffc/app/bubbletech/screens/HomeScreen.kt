package ffc.app.bubbletech.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ffc.app.bubbletech.model.Post
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import ffc.app.bubbletech.data.FakeRepository

@Composable
fun HomeScreen(posts: List<Post>){
    LazyColumn {
        items(posts){ post ->
            Text(text = post.title)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(posts = FakeRepository().getPosts())
}