package com.example.week_ten_task.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.week_ten_task.db.PostDao
import com.example.week_ten_task.db.CommentDao
import com.example.week_ten_task.model.CommentsResponseItem
import com.example.week_ten_task.model.PostResponseItem
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: RetroService,
    private val dao: PostDao,
    private val commentDao: CommentDao
) {
    //uses the injected getPostDao function to access and read posts from post_table
    fun getAllPosts() : LiveData<MutableList<PostResponseItem>> {
        return dao.getAllPosts()
    }

    fun getAllCommentsFromDb() : LiveData<MutableList<CommentsResponseItem>> {
        return commentDao.getComments()
    }

    //Method uses the injected getPostDao function to insert posts to post_table
    private fun insertPosts(postResponseItem: PostResponseItem){
        dao.insertPost(postResponseItem)
    }

    //uses the injected getCommentDao function to access and read comments from comment_table
    fun getComments(post_id: Int) : LiveData<MutableList<CommentsResponseItem>>{
        return commentDao.getAllComments(post_id)
    }

    //Method uses the injected getCommentDao function to insert comments to comment_table
    private fun insertComments(commentsResponseItem: CommentsResponseItem){
        commentDao.insertComment(commentsResponseItem)
    }

    //LiveData observable for singlePosts Network call
    private val _getSinglePostLiveData: MutableLiveData<PostResponseItem> = MutableLiveData()
    val getSinglePostLiveData: LiveData<PostResponseItem> = _getSinglePostLiveData

    suspend fun getAllCommentsFromApi(){
        val response = api.getAllComments()

        if (response.isSuccessful){
            for (i in response.body()!!){
                insertComments(i)
            }
        }
    }

    //Method to get Posts from Network and add to post_table in database
    suspend fun getPostsFromApi() {
        val response = api.getPost()

        if (response.isSuccessful){
            for (i in response.body()!!){
                insertPosts(i)
            }
        }else{
            //TODO
        }
    }

    //Method to get Comments from Network and add to comment_table in database
    suspend fun getCommentFromApi(postId: Int) {
        val response = api.getComments(postId)

        if (response.isSuccessful){
            for (i in response.body()!!){
                insertComments(i)
            }
        }else{
            //TODO
        }
    }

    //Method to add Post to post_table database
    suspend fun addPostToDatabase(postResponseItem: PostResponseItem){
        val response = api.createPost(postResponseItem)

        if (response.isSuccessful){
            insertPosts(response.body()!!)
            Log.d("Repository", "post: $response")
        }else{
            //TODO
        }
    }

    //Method to insert to database
    fun insertPostToDatabase(postResponseItem: PostResponseItem){
        insertPosts(postResponseItem)
    }


    suspend fun addCommentToDatabase(commentsResponseItem: CommentsResponseItem, postId: Int){
        val response = api.postComments(commentsResponseItem, postId)

        if (response.isSuccessful){
            insertComments(response.body()!!)
            Log.d("Comment", "Repository-success: ${response.body()}")
            Log.d("Comment", "Repository-success: $commentsResponseItem")
        }else{
            Log.d("Comment", "Repository-failure: ${response.body()}")
        }
    }

    //Method to insert comment to database
    fun insertCommentToDatabase(commentsResponseItem: CommentsResponseItem){
        insertComments(commentsResponseItem)
    }

    //Method to fetch single post from network
    suspend fun getSinglePost(postId: Int){
        val response = api.getSinglePost(postId)

        if (response.isSuccessful){
            _getSinglePostLiveData.postValue(response.body())
        }else{
            //TODO
        }
    }
}