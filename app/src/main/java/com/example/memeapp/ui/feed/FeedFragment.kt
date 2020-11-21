package com.example.memeapp.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.memeapp.R
import kotlinx.android.synthetic.main.fragment_feed.*
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class FeedFragment : Fragment() {

    private val feedViewModel: FeedViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feedViewModel.text.observe({ lifecycle }, Observer {
            text_dashboard.text = it
        })
        feedViewModel.getFeed()
    }
}