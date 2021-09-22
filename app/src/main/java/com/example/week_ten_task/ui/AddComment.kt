package com.example.week_ten_task.ui

import android.os.Bundle
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
import com.example.week_ten_task.model.CommentsResponseItem
import com.example.week_ten_task.model.PostResponseItem
import com.example.week_ten_task.vm.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddComment : Fragment() {
    private val viewModel: AppViewModel by viewModels()
    lateinit var commentsResponseItem: CommentsResponseItem
    lateinit var title: EditText
    lateinit var email: EditText
    lateinit var submitCommentBtn: AppCompatButton
    lateinit var comment: AppCompatEditText
    lateinit var closeAddComment: ImageButton
    private val argg : AddCommentArgs by navArgs()
    lateinit var postData: PostResponseItem
    private var postId: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_comment, container, false)
        title = view.findViewById(R.id.tvTitleComment)
        email = view.findViewById(R.id.tvEmailComment)
        comment = view.findViewById(R.id.inputCommentTxt)
        submitCommentBtn = view.findViewById(R.id.submitCommentBtn)
        closeAddComment = view.findViewById(R.id.closeAddComment)

        closeAddComment.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return view
    }

    companion object{
        fun instance() = AddComment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postData = argg.postPassed!!
        postId = postData.id

        submitCommentBtn.setOnClickListener {
            if (postId != null){
                createComment(postId!!)
            }
        }
    }

    private fun createComment(postId: Int){
        commentsResponseItem = CommentsResponseItem(postId, 1, title.text.toString(), email.text.toString(), comment.text.toString())
        if (title.text.isEmpty() || email.text.isEmpty() || comment.text?.isEmpty()!!){
            Toast.makeText(requireContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show()
        }else{
            observeComment(commentsResponseItem, postId)
            val action = AddCommentDirections.actionAddCommentToPostPage(postData, postData)
            findNavController().navigate(action)
        }
    }

    private fun observeComment(commentsResponseItem: CommentsResponseItem, postId: Int){
        viewModel.addComment(commentsResponseItem, postId)
    }

}