package com.example.memeapp.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memeapp.databinding.FragmentFeedBinding
import com.example.memeapp.ui.MainViewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class FeedFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val adapter: FeedAdapter by lazy { FeedAdapter(activity) }

    private var binding: FragmentFeedBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.rvMain?.layoutManager = LinearLayoutManager(context)
        binding?.rvMain?.adapter = adapter

        viewModel.feedLoading.observe(viewLifecycleOwner) {
            binding?.loadingView?.isVisible = it
        }

        viewModel.feedLiveData.observe(viewLifecycleOwner) {
            binding?.swiperefresh?.isRefreshing = false
            it?.toMutableList()?.let { list ->
                adapter.feedList = list
            }
            adapter.notifyDataSetChanged()
        }
        binding?.swiperefresh?.setOnRefreshListener {
            viewModel.getFeed()
        }
    }
}