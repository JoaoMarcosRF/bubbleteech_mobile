package ffc.app.bubbletech.network

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.runBlocking

object ApiClient {
    private const val BASE_URL = "http://127.0.0.1:8080/"

    private val http = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val user = FirebaseAuth.getInstance().currentUser
            val token = user?.let {
                try {
                    runBlocking { it.getIdToken(false).await().token }
                } catch (e: Exception) {
                    null
                }
            }

            val requestBuilder = chain.request().newBuilder()
            if (!token.isNullOrEmpty()) {
                requestBuilder.header("Authorization", "Bearer $token")
            }
            chain.proceed(requestBuilder.build())
        }
        .build()

    val api: BubbleApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(http)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BubbleApi::class.java)
}