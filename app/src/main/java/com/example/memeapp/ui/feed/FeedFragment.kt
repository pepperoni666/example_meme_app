package com.example.memeapp.ui.feed

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memeapp.R
import com.example.memeapp.ui.DetailActivity
import com.example.memeapp.ui.DetailActivity.Companion.DETAIL_LIKED
import com.example.memeapp.ui.MainViewModel
import com.example.remote_datasource.feed.FeedItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_feed.swiperefresh
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class FeedFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val adapter: FeedAdapter by lazy { FeedAdapter(activity) }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_main.layoutManager = LinearLayoutManager(context)
        rv_main.adapter = adapter

        viewModel.feedLoading.observe(viewLifecycleOwner, {
            loading_view.isVisible = it
        })

        viewModel.feedLiveData.observe(viewLifecycleOwner, {
            it?.toMutableList()?.let {list ->
                adapter.feedList = list
            }
            adapter.notifyDataSetChanged()
        })
        swiperefresh.setOnRefreshListener {
            swiperefresh.isRefreshing = false
            viewModel.getFeed()
        }
    }
}