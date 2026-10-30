package ffc.app.bubbletech.network

import retrofit2.http.GET
import ffc.app.bubbletech.model.Comment
import ffc.app.bubbletech.model.CommentRequest
import ffc.app.bubbletech.model.CommentsResponse
import ffc.app.bubbletech.model.LikeResponse
import ffc.app.bubbletech.model.NewsArticle
import ffc.app.bubbletech.model.NewsListResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Path

data class MeResponse(
    val uid: String,
    val email: String?,
    val name: String?,
    val photoUrl: String?,
    val createdAt: String
)

interface BubbleApi {
    @GET("me")
    suspend fun me(): MeResponse

    @GET("news")
    suspend fun getNews(
        @Query("category") category: String? = null,
        @Query("search") search: String? = null,
        @Query("limit") limit: Int = 10,
        @Query("cursor") cursor: String? = null
    ): NewsListResponse

    @GET("news/{id}")
    suspend fun getNewsById(@Path("id") id: String): NewsArticle

    @POST("news/{id}/like")
    suspend fun likeNews(@Path("id") id: String): LikeResponse

    @DELETE("news/{id}/like")
    suspend fun unlikeNews(@Path("id") id: String): LikeResponse

    @GET("news/{id}/comments")
    suspend fun getComments(@Path("id") id: String): CommentsResponse

    @POST("news/{id}/comments")
    suspend fun createComment(
        @Path("id") id: String,
        @Body request: CommentRequest
    ): Comment

    @PATCH("news/{id}/comments/{commentId}")
    suspend fun updateComment(
        @Path("id") id: String,
        @Path("commentId") commentId: String,
        @Body request: CommentRequest
    ): Comment

    @DELETE("news/{id}/comments/{commentId}")
    suspend fun deleteComment(
        @Path("id") id: String,
        @Path("commentId") commentId: String
    )
}