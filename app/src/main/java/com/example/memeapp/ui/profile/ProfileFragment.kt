package com.example.memeapp.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.memeapp.R
import com.example.memeapp.ui.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class ProfileFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profileLoading.observe(viewLifecycleOwner, {
            loading_view.isVisible = it
        })

        profileViewModel.nameMutableLiveData.observe({ lifecycle }, {
            profile_name.text = it
            edit_name.setText(it)
            profile_liked_number
        })

        profileViewModel.editingMutableLiveData.observe({ lifecycle }, {
            profile_name.isVisible = !it
            name_edition.isVisible = it
        })

        viewModel.profileLiveData.observe(viewLifecycleOwner, {
            Picasso.get().load(it.pic).into(profile_pic)
            profileViewModel.nameMutableLiveData.postValue(it.name)
        })

        viewModel.feedLiveData.observe(viewLifecycleOwner, {list ->
            val n = list.count { it.liked }
            profile_liked_number.text = context?.resources?.getString(R.string.profile_liked)?.let { String.format(it, n) }
        })

        profile_name.setOnClickListener {
            profileViewModel.editingMutableLiveData.postValue(true)
        }

        save_name_btn.setOnClickListener {
            profileViewModel.nameMutableLiveData.postValue(edit_name.text.toString())
            profileViewModel.editingMutableLiveData.postValue(false)
            hideKeyboard(it)
        }

        cancel_name_btn.setOnClickListener {
            profileViewModel.editingMutableLiveData.postValue(false)
            hideKeyboard(it)
        }

        swiperefresh.setOnRefreshListener {
            swiperefresh.isRefreshing = false
            viewModel.getProfile()
        }
    }

    private fun hideKeyboard(view: View){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}