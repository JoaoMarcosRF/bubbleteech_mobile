package ffc.app.bubbletech.data

import ffc.app.bubbletech.model.CommentRequest
import ffc.app.bubbletech.network.ApiClient
import ffc.app.bubbletech.network.BubbleApi

class NewsRepository(
    private val api: BubbleApi = ApiClient.api
) {
    companion object {
        private val likedNewsIds = mutableSetOf<String>()

        fun isNewsLiked(id: String): Boolean = id in likedNewsIds

        fun setNewsLiked(id: String, liked: Boolean) {
            if (liked) {
                likedNewsIds.add(id)
            } else {
                likedNewsIds.remove(id)
            }
        }
    }

    suspend fun getNews(category: String?, search: String?, cursor: String?) =
        api.getNews(category = category, search = search, cursor = cursor)

    suspend fun getNewsById(id: String) = api.getNewsById(id)

    suspend fun likeNews(id: String) = api.likeNews(id)

    suspend fun unlikeNews(id: String) = api.unlikeNews(id)

    suspend fun getComments(id: String) = api.getComments(id)

    suspend fun createComment(id: String, text: String) =
        api.createComment(id, CommentRequest(text))

    suspend fun updateComment(id: String, commentId: String, text: String) =
        api.updateComment(id, commentId, CommentRequest(text))

    suspend fun deleteComment(id: String, commentId: String) =
        api.deleteComment(id, commentId)
}
