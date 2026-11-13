package ffc.app.bubbletech.model

data class NewsArticle(
    val id: String,
    val title: String,
    val description: String,
    val content: String,
    val url: String,
    val imageUrl: String?,
    val source: String,
    val categories: List<String>,
    val likesCount: Int,
    val commentsCount: Int,
    val publishedAt: String
)

data class NewsListResponse(
    val items: List<NewsArticle>,
    val nextCursor: String?
)

data class CommentsResponse(
    val items: List<Comment>
)

data class Comment(
    val id: String,
    val uid: String,
    val text: String,
    val createdAt: String,
    val updatedAt: String?
)

data class LikeResponse(
    val liked: Boolean,
    val likesCount: Int
)

data class CommentRequest(
    val text: String
)
