package ffc.app.bubbletech.network

import retrofit2.http.GET

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
}