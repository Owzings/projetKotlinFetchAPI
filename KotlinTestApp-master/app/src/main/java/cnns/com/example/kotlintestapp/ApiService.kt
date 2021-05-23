package cnns.com.example.kotlintestapp

import cnns.com.example.kotlintestapp.models.Comment
import cnns.com.example.kotlintestapp.models.Post
import cnns.com.example.kotlintestapp.models.User
import cnns.com.example.kotlintestapp.models.Address
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<MutableList<User>>

    @GET("posts/{num}")
    suspend fun getPostById(@Path("num") num : Int): Response<Post>

    @GET("comments")
    suspend fun getCommentsByPost(@Query("postId") postId : Int): Response<MutableList<Comment>>

    @POST("posts")
    suspend fun createPost(@Body post: Post): Response<Post>
}