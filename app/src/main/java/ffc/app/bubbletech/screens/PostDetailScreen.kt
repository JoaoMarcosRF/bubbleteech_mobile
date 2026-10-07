package ffc.app.bubbletech.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ffc.app.bubbletech.data.FakeRepository
import ffc.app.bubbletech.model.Post
import ffc.app.bubbletech.ui.theme.BubbleTechTheme
import ffc.app.bubbletech.ui.theme.DarkText
import ffc.app.bubbletech.ui.theme.ExtraLightGray
import ffc.app.bubbletech.ui.theme.GrayText
import ffc.app.bubbletech.ui.theme.SecondaryBlue

@Composable
fun PostDetailScreen(post: Post){

    val firstNews = post.news.first()
    val initials = firstNews.source
        .split(" ")
        .take(2)
        .joinToString(""){it.first().uppercase()}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(SecondaryBlue)
        )
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-24).dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
                .padding(24.dp)
        ) {
            Text(
                text = post.title,
                color = DarkText,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Fonte: bolinha com as iniciais, nome e data
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(ExtraLightGray)
                ) {
                    Text(text = initials, color = DarkText, style = MaterialTheme.typography.labelSmall)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = firstNews.source,
                    color = DarkText,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(text = firstNews.publishedAt, color = GrayText, style = MaterialTheme.typography.labelSmall)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Contadores: comentários e compartilhar
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = null, tint = GrayText, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "${post.commentCount} comentários", color = GrayText, style = MaterialTheme.typography.labelMedium)

                Spacer(modifier = Modifier.width(16.dp))

                Icon(Icons.Outlined.Share, contentDescription = null, tint = GrayText, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Share", color = GrayText, style = MaterialTheme.typography.labelMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = ExtraLightGray)

            Spacer(modifier = Modifier.height(16.dp))

            // Texto da notícia: por enquanto, o resumo de cada notícia do post vira um parágrafo (dado mockado)
            post.news.forEach { news ->
                Text(
                    text = news.summary,
                    color = DarkText,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PostDetailPreview(){
    BubbleTechTheme {
        PostDetailScreen(post = FakeRepository().getPosts().first())
    }
}