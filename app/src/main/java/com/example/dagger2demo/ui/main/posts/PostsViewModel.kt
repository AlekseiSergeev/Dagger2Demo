package com.example.dagger2demo.ui.main.posts

import android.util.Log
import androidx.lifecycle.*
import com.example.dagger2demo.SessionManager
import com.example.dagger2demo.models.Post
import com.example.dagger2demo.network.main.MainApi
import com.example.dagger2demo.ui.main.Resource
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val mainApi: MainApi
) : ViewModel() {
    private val TAG = "PostsViewModel"
    private var posts: MediatorLiveData<Resource<List<Post>>>? = null

    fun observePosts(): LiveData<Resource<List<Post>>> {
        if(posts == null) {
            posts = MediatorLiveData()
            posts?.value = Resource.Loading
            val source = LiveDataReactiveStreams.fromPublisher(
                mainApi.getPostsFromUser(sessionManager.getAuthUser().value?.data?.id)
                    .onErrorReturn(object : Function<Throwable, List<Post>> {
                        override fun apply(t: Throwable): List<Post> {
                            Log.e(TAG, "apply: $t")
                            val post = Post(-1, -1, null, null)
                            val posts = ArrayList<Post>()
                            posts.add(post)
                            return posts
                        }
                    })
                    .map(object : Function<List<Post>, Resource<List<Post>>> {
                        override fun apply(posts: List<Post>): Resource<List<Post>> {
                            if (posts.isNotEmpty()) {
                                if (posts[0].id == -1) {
                                    return Resource.Error("Something went wrong")
                                }
                            }
                            return Resource.Success(posts)
                        }
                    })
                    .subscribeOn(Schedulers.io())
            )
            posts?.addSource(source, object : Observer<Resource<List<Post>>> {
                override fun onChanged(listResource: Resource<List<Post>>?) {
                    posts?.value = listResource
                    posts?.removeSource(source)
                }
            })
        }
        return posts as MediatorLiveData<Resource<List<Post>>>
    }
}