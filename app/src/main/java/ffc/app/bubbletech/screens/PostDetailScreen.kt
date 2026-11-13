package ffc.app.bubbletech.screens

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ffc.app.bubbletech.model.Comment
import ffc.app.bubbletech.model.NewsArticle
import ffc.app.bubbletech.ui.theme.BubbleTechTheme
import ffc.app.bubbletech.ui.theme.DarkText
import ffc.app.bubbletech.ui.theme.ExtraLightGray
import ffc.app.bubbletech.ui.theme.GrayText
import ffc.app.bubbletech.ui.theme.LightBlue
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

private val dateFormatter = DateTimeFormatter
    .ofPattern("dd/MM/yyyy 'às' HH:mm")
    .withZone(ZoneId.systemDefault())

private fun formatDate(date: String): String {
    return try {
        dateFormatter.format(Instant.parse(date))
    } catch (e: DateTimeParseException) {
        date
    }
}

@Composable
fun PostDetailScreen(
    articleId: String,
    postDetailViewModel: PostDetailViewModel = viewModel()
) {
    val state by postDetailViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(articleId) {
        postDetailViewModel.load(articleId)
    }

    if (state.loading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Carregando...", color = DarkText)
        }
        return
    }

    val article = state.article ?: run {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(state.error ?: "Notícia não encontrada", color = DarkText)
        }
        return
    }

    PostDetailContent(
        article = article,
        comments = state.comments,
        liked = state.liked,
        commentError = state.commentError,
        onLikeClick = postDetailViewModel::toggleLike,
        onAddComment = postDetailViewModel::addComment,
        onEditComment = postDetailViewModel::editComment,
        onDeleteComment = postDetailViewModel::deleteComment,
        canEditComment = postDetailViewModel::isCurrentUser
    )
}

@Composable
private fun PostDetailContent(
    article: NewsArticle,
    comments: List<Comment>,
    liked: Boolean,
    commentError: String?,
    onLikeClick: () -> Unit,
    onAddComment: (String) -> Unit,
    onEditComment: (String, String) -> Unit,
    onDeleteComment: (String) -> Unit,
    canEditComment: (Comment) -> Boolean
) {
    val context = LocalContext.current
    var commentText by remember { mutableStateOf("") }
    var editingCommentId by remember { mutableStateOf<String?>(null) }
    var editingText by remember { mutableStateOf("") }
    var descriptionExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = article.imageUrl,
            contentDescription = article.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = article.title,
                color = DarkText,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = article.description,
                color = GrayText,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = if (descriptionExpanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Clip
            )
            Text(
                text = if (descriptionExpanded) "Ler menos" else "Ler mais",
                color = LightBlue,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable {
                        descriptionExpanded = !descriptionExpanded
                    }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "${article.source} • ${formatDate(article.publishedAt)}",
                color = GrayText,
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onLikeClick) {
                    Icon(
                        imageVector = if (liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Curtir",
                        tint = if (liked) Color.Red else GrayText
                    )
                }
                Text("${article.likesCount}", color = GrayText)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Outlined.ChatBubbleOutline, null, tint = GrayText, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("${article.commentsCount}", color = GrayText)
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(
                    onClick = {
                        if (article.url.isBlank()) {
                            Toast.makeText(
                                context,
                                "Link da notícia indisponível",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            try {
                                context.startActivity(
                                    Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                                )
                            } catch (e: ActivityNotFoundException) {
                                Toast.makeText(
                                    context,
                                    "Não foi possível abrir o link",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                ) {
                    Icon(
                        Icons.Outlined.Share,
                        contentDescription = "Abrir notícia completa",
                        tint = GrayText
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = ExtraLightGray)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = article.content,
                color = DarkText,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Comentários (${article.commentsCount})",
                color = DarkText,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(ExtraLightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Eu", color = DarkText, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedTextField(
                    value = commentText,
                    onValueChange = { if (it.length <= 1000) commentText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Escreva um comentário...") },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                onAddComment(commentText)
                                commentText = ""
                            },
                            enabled = commentText.isNotBlank()
                        ) {
                            Icon(Icons.Filled.Send, "Enviar comentário", tint = LightBlue)
                        }
                    },
                    shape = RoundedCornerShape(28.dp),
                    singleLine = true
                )
            }
            commentError?.let {
                Text(it, color = Color.Red, style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(20.dp))
            comments.forEach { comment ->
                CommentItem(
                    comment = comment,
                    editable = canEditComment(comment),
                    editing = editingCommentId == comment.id,
                    editingText = editingText,
                    onEdit = {
                        editingCommentId = comment.id
                        editingText = comment.text
                    },
                    onEditingTextChange = { editingText = it },
                    onSaveEdit = {
                        onEditComment(comment.id, editingText)
                        editingCommentId = null
                    },
                    onDelete = { onDeleteComment(comment.id) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun CommentItem(
    comment: Comment,
    editable: Boolean,
    editing: Boolean,
    editingText: String,
    onEdit: () -> Unit,
    onEditingTextChange: (String) -> Unit,
    onSaveEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(ExtraLightGray),
                contentAlignment = Alignment.Center
            ) {
                Text("U", color = DarkText, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(comment.text, color = DarkText, style = MaterialTheme.typography.bodyMedium)
                Text(formatDate(comment.createdAt), color = GrayText, style = MaterialTheme.typography.labelSmall)
            }
            if (editable) {
                var menuExpanded by remember { mutableStateOf(false) }
                Box {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Filled.MoreVert, "Opções do comentário", tint = GrayText)
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Editar") },
                            onClick = {
                                menuExpanded = false
                                onEdit()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Excluir") },
                            onClick = {
                                menuExpanded = false
                                onDelete()
                            }
                        )
                    }
                }
            }
        }
        if (editing) {
            OutlinedTextField(
                value = editingText,
                onValueChange = {
                    if (it.length <= 1000) onEditingTextChange(it)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = onSaveEdit) {
                        Icon(Icons.Filled.Send, "Salvar comentário", tint = LightBlue)
                    }
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PostDetailPreview() {
    BubbleTechTheme {
        PostDetailContent(
            article = NewsArticle(
                id = "preview",
                title = "Notícia de exemplo",
                description = "Resumo da notícia",
                content = "Conteúdo completo da notícia.",
                url = "",
                imageUrl = null,
                source = "BubbleTech",
                categories = listOf("IA"),
                likesCount = 12,
                commentsCount = 0,
                publishedAt = "2026-10-30"
            ),
            comments = emptyList(),
            liked = false,
            commentError = null,
            onLikeClick = {},
            onAddComment = {},
            onEditComment = { _, _ -> },
            onDeleteComment = {},
            canEditComment = { false }
        )
    }
}
