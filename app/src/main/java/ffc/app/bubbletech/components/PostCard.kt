package ffc.app.bubbletech.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ffc.app.bubbletech.data.FakeRepository
import ffc.app.bubbletech.model.Post

@Composable
fun PostCard(post: Post){
    Card {
        Column {
            Text(text = post.category)
            Text(text = post.title)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostCardPreview(){
    PostCard(post = FakeRepository().getPosts().first())
}