package com.example.week_ten_task.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week_ten_task.R
import com.example.week_ten_task.adapter.UserMomentAdapter
import com.example.week_ten_task.adapter.UserPostAdapter
import com.example.week_ten_task.model.PostResponseItem
import com.example.week_ten_task.model.UserMomentModel
import com.example.week_ten_task.vm.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), UserPostAdapter.OnPostClickListener {

    private lateinit var toolbar: Toolbar
    private lateinit var addPostBtn: ImageButton
    lateinit var momentList: ArrayList<UserMomentModel>
    lateinit var momentsRecyclerView: RecyclerView
    lateinit var postRecyclerView: RecyclerView
    lateinit var postAdapter: UserPostAdapter
    lateinit var filterBtn: ImageButton
    lateinit var searchBtn: SearchView

    private val viewmodel: AppViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        toolbar = view.findViewById(R.id.toolbarMain)
        addPostBtn = view.findViewById(R.id.addPostBtn)
        momentsRecyclerView = view.findViewById(R.id.momentsViewPager)
        postRecyclerView = view.findViewById(R.id.postRecyclerView)
        filterBtn = view.findViewById(R.id.filterBtn)
        searchBtn = view.findViewById(R.id.searchBtn)

        momentList = ArrayList()
        momentList()

        initMomentRecyclerView()
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)

        filterBtn.setOnClickListener {
            filterBtn.visibility = View.GONE
            searchBtn.visibility = View.VISIBLE
        }

        searchBtn.setOnCloseListener {
            val isClosed: Boolean
            if (!searchBtn.isFocused){
                searchBtn.visibility = View.GONE
                filterBtn.visibility = View.VISIBLE
                isClosed = true
            }else{
                searchBtn.visibility = View.VISIBLE
                filterBtn.visibility = View.GONE
                isClosed = false
            }
            return@setOnCloseListener isClosed
        }

        addPostBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addPost2)
        }
        return view
    }


    private fun initPostRecyclerview(){
        postRecyclerView.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initMomentRecyclerView(){
        momentsRecyclerView.apply {
            adapter = UserMomentAdapter(momentList)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun momentList(){
        momentList = arrayListOf(
            UserMomentModel(R.drawable.ic_image_one_round, "Abass"),
            UserMomentModel(R.drawable.ic_image_two_round, "Kufre"),
            UserMomentModel(R.drawable.ic_image_three_round, "Mohammad"),
            UserMomentModel(R.drawable.ic_image_four_round, "Chineye"),
            UserMomentModel(R.drawable.ic_image_five_round, "Victor"),
            UserMomentModel(R.drawable.ic_image_six_round, "Johson"),
            UserMomentModel(R.drawable.ic_image_seven_round, "Ilori"),
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeResponse(){
        viewmodel.getPostList().observe(requireActivity(), Observer {
            if (it != null){
                UserPostAdapter.postList = it
                postAdapter.notifyDataSetChanged()
                Log.d("HomeFragment", "${UserPostAdapter.postList}")

            }else{
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        })
        viewmodel.getPostFromApi(requireContext())
    }

    companion object{
        fun instance() = HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postAdapter = UserPostAdapter(this)
        initPostRecyclerview()
        observeResponse()
    }

    override fun onPostClicked(position: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToPostPage(UserPostAdapter.postList[position], null)
        findNavController().navigate(action)
        Log.d("RecClicked", "This View is clicked")
    }

}