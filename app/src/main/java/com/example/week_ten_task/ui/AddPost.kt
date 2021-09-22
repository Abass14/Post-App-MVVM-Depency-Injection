package com.example.week_ten_task.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.week_ten_task.R
import com.example.week_ten_task.adapter.CommentAdapter
import com.example.week_ten_task.adapter.UserPostAdapter
import com.example.week_ten_task.model.PostResponseItem
import com.example.week_ten_task.util.UiFunctions
import com.example.week_ten_task.vm.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPost : Fragment(), UserPostAdapter.OnPostClickListener {

    private lateinit var closeBtn: ImageButton
    private lateinit var submitPostBtn: AppCompatButton
    private lateinit var titleTxt: EditText
    private lateinit var postTxt: AppCompatEditText
    lateinit var postResponseItem: PostResponseItem
    lateinit var postAdapter: UserPostAdapter

    private val viewmodel: AppViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add_post, container, false)

        closeBtn = view.findViewById(R.id.closeAddPost)
        submitPostBtn = view.findViewById(R.id.submitPostBtn)
        titleTxt = view.findViewById(R.id.tvTitle)
        postTxt = view.findViewById(R.id.inputPostTxt)
        postAdapter = UserPostAdapter(this)

        closeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addPost_to_homeFragment)
        }

        submitPostBtn.setOnClickListener {
            createPost()
        }
        return view
    }

    private fun createPost(){
        postResponseItem = PostResponseItem(1,UserPostAdapter.postList.size, titleTxt.text.toString(), postTxt.text.toString())
        if (titleTxt.text.isEmpty() || postTxt.text?.isEmpty()!!){
            Toast.makeText(requireContext(), "Fields can't be empty", Toast.LENGTH_SHORT).show()
        }else{
            observeAddPostResponse(postResponseItem)
            Log.d("AddedPost", "$postResponseItem")
            this.findNavController().navigate(R.id.action_addPost_to_homeFragment)
        }
    }

    private fun observeAddPostResponse(postResponseItem: PostResponseItem){
        viewmodel.addPost(postResponseItem)
    }

    companion object{
        fun instance() = AddPost()
    }

    override fun onPostClicked(position: Int) {
        TODO("Not yet implemented")
    }

}