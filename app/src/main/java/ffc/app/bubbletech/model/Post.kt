package ffc.app.bubbletech.model

data class Post(

    val id: Int,
    val title: String,
    val category: String,
    val news: List<News>,
    val likeCount: Int,
    val commentCount: Int

)
