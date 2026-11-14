package ffc.app.bubbletech.components

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CallMade
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.ArrowCircleUp
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ffc.app.bubbletech.model.NewsArticle
import ffc.app.bubbletech.ui.theme.BubbleTechTheme
import ffc.app.bubbletech.ui.theme.CardBlue

@Composable
fun PostCardAlternative(
    post: NewsArticle,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardBlue),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(post.imageUrl)
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .crossfade(true)
                    .build(),
                contentDescription = post.title,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0x990A1A3A),
                                Color(0xE6071126)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .matchParentSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Schedule,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = post.publishedAt.ifBlank { "9 min" },
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 13.sp
                        )
                    }
                }

                Text(
                    text = post.title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 24.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = post.source.take(1).uppercase(),
                                style = MaterialTheme.typography.labelMedium,
                                color = Color(0xFF071126),
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                        Text(
                            text = post.source,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector =Icons.Outlined.Bookmark,
                            contentDescription = "Salvar",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                        Icon(
                            imageVector = Icons.Outlined.ArrowCircleUp,
                            contentDescription = "Lido",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .background(Color.White, CircleShape)
                                .clickable {
                                    if (post.url.isNotBlank()) {
                                        try {
                                            context.startActivity(
                                                Intent(Intent.ACTION_VIEW, Uri.parse(post.url))
                                            )
                                        } catch (_: ActivityNotFoundException) {
                                            Toast.makeText(
                                                context,
                                                "Não foi possível abrir o link",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.CallMade,
                                contentDescription = "Abrir notícia",
                                tint = Color(0xFF071126),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00174E)
@Composable
fun PostCardAlternativePreview() {
    BubbleTechTheme {
        PostCardAlternative(
            post = NewsArticle(
                id = "preview",
                title = "Geração alpha está viciada em “farmar aura” entenda melhor o “meme” da juventude.",
                description = "Resumo da notícia",
                content = "Conteúdo da notícia",
                url = "",
                imageUrl = "https://i.ytimg.com/vi/7npzLJOThF4/hq720.jpg?sqp=-oaymwEnCNAFEJQDSFryq4qpAxkIARUAAIhCGAHYAQHiAQoIGBACGAY4AUAB&rs=AOn4CLDNo4s9Bj-pL3hLqYsQVft1-Wx2cQ",
                source = "CNN",
                categories = listOf("Geral"),
                likesCount = 0,
                commentsCount = 0,
                publishedAt = "9 min"
            )
        )
    }
}