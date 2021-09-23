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
import androidx.navigation.fragment.navArgs
import com.example.week_ten_task.R
import com.example.week_ten_task.adapter.UserPostAdapter
import com.example.week_ten_task.model.PostResponseItem
import com.example.week_ten_task.vm.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPost : Fragment() {

    //Initializing variables
    private lateinit var closeBtn: ImageButton
    private lateinit var submitPostBtn: AppCompatButton
    private lateinit var titleTxt: EditText
    private lateinit var postTxt: AppCompatEditText
    lateinit var postResponseItem: PostResponseItem
    lateinit var postList: Array<PostResponseItem>

    //initializing argument and viewModel by delegate
    private val args: AddPostArgs by navArgs()
    private val viewModel: AppViewModel by viewModels()

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

        //setting click listener on close button
        closeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addPost_to_homeFragment)
        }

        //click listener on submitPost button
        submitPostBtn.setOnClickListener {
            createPost()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Receiving arguments from Home Fragment
        postList = args.postList
    }

    //Function to add new Post
    private fun createPost(){
        postResponseItem = PostResponseItem(1,postList.size + 1, titleTxt.text.toString(), postTxt.text.toString())
        if (titleTxt.text.isEmpty() || postTxt.text?.isEmpty()!!){
            Toast.makeText(requireContext(), "Fields can't be empty", Toast.LENGTH_SHORT).show()
        }else{
            viewModel. insertPost(postResponseItem)
            Log.d("AddedPost", "$postResponseItem")
            this.findNavController().navigate(R.id.action_addPost_to_homeFragment)
        }
    }

    companion object{
        fun instance() = AddPost()
    }

}