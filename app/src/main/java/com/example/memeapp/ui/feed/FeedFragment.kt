package com.example.memeapp.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.example.memeapp.R
import com.example.memeapp.ui.DetailActivity
import com.example.memeapp.ui.MainViewModel
import com.example.remote_datasource.feed.FeedItem
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_feed.swiperefresh
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class FeedFragment : Fragment(), MavericksView {

    private val viewModel: MainViewModel by activityViewModel()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                result.data?.getParcelableExtra<FeedItem>(DetailActivity.DETAIL_LIKED)?.let {
                    viewModel.likeMeme(it)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFeed()

        swiperefresh.setOnRefreshListener {
            swiperefresh.isRefreshing = false
            viewModel.getFeed()
        }
    }

    override fun invalidate() {
        withState(viewModel) { state ->
            loading_view.isVisible = state.feed is Loading
            rv_main.withModels {
                state.feed()?.feedList?.forEach { item ->
                    memeRow {
                        id(item.id)
                        data(item)
                        clickListener(View.OnClickListener {
                            startForResult.launch(DetailActivity.getIntent(requireContext(), item))
                        })
                    }
                }
            }
        }
    }
}