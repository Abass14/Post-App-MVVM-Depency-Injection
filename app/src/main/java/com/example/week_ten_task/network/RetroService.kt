package com.example.week_ten_task.network

import com.example.week_ten_task.model.CommentsResponse
import com.example.week_ten_task.model.CommentsResponseItem
import com.example.week_ten_task.model.PostResponse
import com.example.week_ten_task.model.PostResponseItem
import com.example.week_ten_task.util.COMMENTS
import com.example.week_ten_task.util.COMMENT_EP
import com.example.week_ten_task.util.POST_EP
import com.example.week_ten_task.util.SINGLE_POST
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetroService {

    @GET(POST_EP)
    suspend fun getPost() : Response<PostResponse>

    @GET(COMMENTS)
    suspend fun getAllComments() : Response<CommentsResponse>


    @GET(COMMENT_EP)
    suspend fun getComments(@Path("post_id") postId: Int) : Response<CommentsResponse>

    @GET(SINGLE_POST)
    suspend fun getSinglePost(@Path("post_id") postId: Int) : Response<PostResponseItem>

    @POST(POST_EP)
    suspend fun createPost(@Body params: PostResponseItem) : Response<PostResponseItem>

    @POST(COMMENT_EP)
    suspend fun postComments(@Body params: CommentsResponseItem, @Path("post_id") postId: Int) : Response<CommentsResponseItem>
}