package com.example.memeapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.memeapp.R
import com.example.remote_datasource.feed.FeedItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity: AppCompatActivity() {

    private val mScaleGestureDetector: ScaleGestureDetector by lazy {
        ScaleGestureDetector(
            this,
            ScaleListener()
        ) }
    private var mScaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val feedItem = intent.extras?.get(DETAIL_EXTRA_ITEM) as FeedItem
        Picasso.get().load(feedItem.imageUrl).into(meme)
        meme_title.text = feedItem.title
        supportActionBar?.title = getString(R.string.title_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if(feedItem.liked){
            like_btn.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryLight))
        }
        like_btn.setOnClickListener {
            val returnIntent = Intent()
            returnIntent.putExtra(DETAIL_LIKED, feedItem)
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return mScaleGestureDetector.onTouchEvent(event)
    }

    inner class ScaleListener : SimpleOnScaleGestureListener() {
        // when a scale gesture is detected, use it to resize the image
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            mScaleFactor *= scaleGestureDetector.scaleFactor
            meme.scaleX = mScaleFactor
            meme.scaleY = mScaleFactor
            return true
        }
    }

    companion object{

        const val DETAIL_EXTRA_ITEM = "DETAIL_EXTRA_ITEM"
        const val DETAIL_LIKED = "DETAIL_LIKED"

        fun getIntent(context: Context, item: FeedItem): Intent{
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(DETAIL_EXTRA_ITEM, item)
            }
        }
    }
}