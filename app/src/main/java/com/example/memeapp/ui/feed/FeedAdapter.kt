package com.example.memeapp.ui.feed

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.memeapp.R
import com.example.memeapp.ui.DetailActivity
import com.example.memeapp.ui.DetailActivity.Companion.DETAIL_REQUEST
import com.example.remote_datasource.feed.FeedItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.feed_item.view.*


class FeedAdapter(private val context: Activity?): RecyclerView.Adapter<FeedAdapter.FeedItemViewHolder>() {

    var feedList: MutableList<FeedItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.FeedItemViewHolder {
        val rootView: View = LayoutInflater.from(context).inflate(R.layout.feed_item, parent, false)
        return FeedItemViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: FeedAdapter.FeedItemViewHolder, position: Int) {
        val item: FeedItem = feedList[position]
        holder.setData(item)
        holder.itemView.setOnClickListener {
            context?.let {
                it.startActivityForResult(DetailActivity.getIntent(it, item), DETAIL_REQUEST)
            }
        }
    }

    override fun getItemCount() = feedList.size

    inner class FeedItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun setData(item: FeedItem){
            Picasso.get().load(item.imageUrl).into(itemView.meme)
            itemView.meme_title.text = item.title
            itemView.meme_likes.text = context?.resources?.getString(R.string.feed_item_likes)?.let { String.format(it, item.likes) }
            itemView.meme_liked.isVisible = item.liked
        }
    }
}