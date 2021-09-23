package com.example.week_ten_task.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week_ten_task.ui.PostPageArgs
import com.example.week_ten_task.R
import com.example.week_ten_task.adapter.CommentAdapter
import com.example.week_ten_task.model.CommentsResponseItem
import com.example.week_ten_task.model.PostResponseItem
import com.example.week_ten_task.vm.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

@AndroidEntryPoint
class PostPage : Fragment() {
    //Late initializing
    lateinit var thePostTitle: TextView
    lateinit var thePostBody: TextView
    lateinit var backBtnPost: ImageButton
    lateinit var commentBtn_post: ImageButton
    private var postId: Int? = null
    lateinit var commentRecyclerView: RecyclerView
    lateinit var commentAdapter: CommentAdapter
    private val args : PostPageArgs by navArgs()
    private val viewModel: AppViewModel by viewModels()
    lateinit var postData: PostResponseItem
    lateinit var commList: MutableList<CommentsResponseItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_post_page, container, false)
        //Initializing views
        thePostTitle = view.findViewById(R.id.postPageTxt)
        thePostBody = view.findViewById(R.id.postPageBody)
        backBtnPost = view.findViewById(R.id.backBtnPost)
        commentBtn_post = view.findViewById(R.id.commentBtn_post)
        commentRecyclerView = view.findViewById(R.id.commentRecyclerView)
        commentAdapter = CommentAdapter()
        commList = mutableListOf()

        //setting click listener to back button
        backBtnPost.setOnClickListener {
            findNavController().navigate(R.id.action_postPage_to_homeFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Checking if received arguments isn't null and using arg to trigger network call to fetch comments and single post
            if (args.postData != null){
                postData = args.postData!!
                thePostTitle.text = postData.theTitle
                postId = postData.id
                thePostBody.text = postData.theBody

                initRecyclerView()
                insertAllComments()

                if (postId != null) {
                    observeCommentLiveData(postId!!)
                }

                commentBtn_post.setOnClickListener {
                    val actions = PostPageDirections.actionPostPageToAddComment(postData,
                        commList.toTypedArray()
                    )
                    findNavController().navigate(actions)
                }
            }else{
                postData = args.secondPostData!!
                postId = postData.id
                if (postId != null){
                    observeSinglePost(postId!!)
                }
                commentAdapter = CommentAdapter()

                initRecyclerView()
                insertAllComments()

                if (postId != null) {
                    observeCommentLiveData(postId!!)
                }


                commentBtn_post.setOnClickListener {
                    val actions = PostPageDirections.actionPostPageToAddComment(postData,
                        commList.toTypedArray()
                    )
                    findNavController().navigate(actions)
                }
            }



    }

    companion object{
        fun instance() = PostPage()
    }

    fun navigateWithData(){
    }
    private fun initRecyclerView(){
        commentRecyclerView.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun bindPostViews(postResponseItem: PostResponseItem){
        thePostTitle.text = postResponseItem.theTitle
        thePostBody.text = postResponseItem.theBody
    }

    @SuppressLint("NotifyDataSetChanged")
    fun observeCommentLiveData(postId: Int){
        viewModel.getCommentList(postId).observe(requireActivity(), Observer {
            if (it != null){
                commentAdapter.commentList = it
                commentAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun insertAllComments(){
        viewModel.getAllCommentList().observe(requireActivity(), Observer {
            if (it != null){
                commList = it
            }
        })
        lifecycleScope.launch {
            viewModel.getAllCommentFromApi()
        }

    }

    private fun observeSinglePost(postId: Int){
        viewModel.getSinglePost().observe(requireActivity(), Observer {
            if (it !=null){
                bindPostViews(it)
            }
        })
        viewModel.getSinglePostCall(postId)
    }

}