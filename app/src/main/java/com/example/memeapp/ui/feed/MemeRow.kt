package com.example.memeapp.ui.feed

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.example.memeapp.R
import com.example.remote_datasource.feed.FeedItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.feed_item.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class MemeRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.feed_item, this, true)
    }

    @ModelProp
    fun setData(item: FeedItem) {
        Picasso.get().load(item.imageUrl).into(meme)
        meme_title.text = item.title
        meme_likes.text = context?.resources?.getString(R.string.feed_item_likes)
            ?.let { String.format(it, item.likes) }
        meme_liked.isVisible = item.liked
    }

    @CallbackProp
    fun setClickListener(listener: OnClickListener?) {
        listener?.let {
            item_root.setOnClickListener(it)
        }
    }
}