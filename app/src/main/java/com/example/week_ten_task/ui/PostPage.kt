package com.example.week_ten_task.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week_ten_task.ui.PostPageArgs
import com.example.week_ten_task.R
import com.example.week_ten_task.adapter.CommentAdapter
import com.example.week_ten_task.model.PostResponseItem
import com.example.week_ten_task.vm.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostPage : Fragment() {
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_post_page, container, false)
        thePostTitle = view.findViewById(R.id.postPageTxt)
        thePostBody = view.findViewById(R.id.postPageBody)
        backBtnPost = view.findViewById(R.id.backBtnPost)
        commentBtn_post = view.findViewById(R.id.commentBtn_post)
        commentRecyclerView = view.findViewById(R.id.commentRecyclerView)

        backBtnPost.setOnClickListener {
            findNavController().navigate(R.id.action_postPage_to_homeFragment)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//            val postData1 = args.postData
//            val postData2 = args.postId
            if (args.postData != null){
                postData = args.postData!!
                thePostTitle.text = postData.theTitle
                postId = postData.id
                thePostBody.text = postData.theBody
                commentAdapter = CommentAdapter()

                initRecyclerView()
                if (postId != null) {
                    observeCommentLiveData(postId!!)
                }

                commentBtn_post.setOnClickListener {
                    val actions = PostPageDirections.actionPostPageToAddComment(postData)
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
                if (postId != null) {
                    observeCommentLiveData(postId!!)
                }

                commentBtn_post.setOnClickListener {
                    val actions = PostPageDirections.actionPostPageToAddComment(postData)
                    findNavController().navigate(actions)
                }
            }



    }

    companion object{
        fun instance() = PostPage()
    }

    fun navigateWithData(){
    }
    fun initRecyclerView(){
        commentRecyclerView.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    fun bindPostViews(postResponseItem: PostResponseItem){
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

        viewModel.getCommentFromApi(postId)
    }

    fun observeSinglePost(postId: Int){
        viewModel.getSinglePost().observe(requireActivity(), Observer {
            if (it !=null){
                bindPostViews(it)
            }
        })
        viewModel.getSinglePostCall(postId)
    }

}