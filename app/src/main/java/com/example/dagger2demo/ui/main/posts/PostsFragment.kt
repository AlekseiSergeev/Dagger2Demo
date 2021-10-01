package com.example.dagger2demo.ui.main.posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dagger2demo.R
import com.example.dagger2demo.models.Post
import com.example.dagger2demo.ui.main.Resource
import com.example.dagger2demo.util.VerticalSpaceItemDecoration
import com.example.dagger2demo.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PostsFragment: DaggerFragment() {
    private val TAG = "PostsFragments"
    private lateinit var viewModel: PostsViewModel
    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    @Inject
    lateinit var postAdapter: PostRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView= view.findViewById(R.id.recycler_view)

        viewModel= ViewModelProvider(this, providerFactory).get(PostsViewModel::class.java)

        initRecyclerView()
        subscribeObservers()
    }

    private fun subscribeObservers(){
        viewModel.observePosts().removeObservers(viewLifecycleOwner)
        viewModel.observePosts().observe(viewLifecycleOwner, object : Observer<Resource<List<Post>>>{
            override fun onChanged(listResource: Resource<List<Post>>?) {
                if(listResource != null){
                    when(listResource){
                        is Resource.Loading -> { Log.i(TAG, "onChanged: LOADING...")}
                        is Resource.Success -> {
                            Log.i(TAG, "onChanged: got posts ${listResource.data}")
                            postAdapter.setPosts(listResource.data)
                        }
                        is Resource.Error -> { Log.e(TAG, "onChanged: Error ${listResource.message}")}
                    }
                }
            }
        })
    }

    private fun initRecyclerView() {
        val itemDecoration = VerticalSpaceItemDecoration(15)
        recyclerView.apply {
            adapter = postAdapter
            addItemDecoration(itemDecoration)
            layoutManager = LinearLayoutManager(activity)
        }
    }
}