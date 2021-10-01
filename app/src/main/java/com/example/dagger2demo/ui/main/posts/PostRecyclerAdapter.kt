package com.example.dagger2demo.ui.main.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dagger2demo.R
import com.example.dagger2demo.models.Post
import kotlinx.android.synthetic.main.layout_post_list_item.view.*
import java.util.*


class PostRecyclerAdapter : ListAdapter<Post, PostRecyclerAdapter.PostViewHolder>(PostDiffCallback()) {
    private var posts: List<Post> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_post_list_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostRecyclerAdapter.PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun setPosts(posts: List<Post>?) {
        if (posts != null) {
            this.posts = posts
            submitList(posts)
        }
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.title
        fun bind(post: Post) {
            title.text = post.title
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.title == newItem.title
    }
}
