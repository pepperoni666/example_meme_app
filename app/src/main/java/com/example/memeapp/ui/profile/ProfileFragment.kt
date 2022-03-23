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
import com.example.memeapp.databinding.FragmentProfileBinding
import com.example.memeapp.ui.MainViewModel
import com.squareup.picasso.Picasso
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class ProfileFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    private var binding: FragmentProfileBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    private fun observeViewModel(){
        viewModel.profileLoading.observe(viewLifecycleOwner) {
            binding?.loadingView?.isVisible = it
        }

        viewModel.profileLiveData.observe(viewLifecycleOwner) {
            Picasso.get().load(it.pic).into(binding?.profilePic)
            binding?.profileName?.text = it.name
            binding?.editName?.setText(it.name)
            profileViewModel.profile = it
        }

        viewModel.feedLiveData.observe(viewLifecycleOwner) { list ->
            val n = list.count { it.liked }
            binding?.profileLikedNumber?.text =
                context?.resources?.getString(R.string.profile_liked)?.let { String.format(it, n) }
        }
    }

    private fun observeProfileViewModel(){
        profileViewModel.loading.observe({ lifecycle }, {
            binding?.loadingView?.isVisible = it
        })

        profileViewModel.editingMutableLiveData.observe({ lifecycle }, {
            binding?.profileName?.isVisible = !it
            binding?.nameEdition?.isVisible = it
        })

        profileViewModel.updatedProfileLiveData.observe({ lifecycle }, {
            viewModel.updateProfile(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        observeProfileViewModel()

        binding?.profileName?.setOnClickListener {
            profileViewModel.editingMutableLiveData.postValue(true)
        }

        binding?.saveNameBtn?.setOnClickListener {
            profileViewModel.updateName(binding?.editName?.text.toString())
            profileViewModel.editingMutableLiveData.postValue(false)
            hideKeyboard(it)
        }

        binding?.cancelNameBtn?.setOnClickListener {
            profileViewModel.editingMutableLiveData.postValue(false)
            hideKeyboard(it)
        }

        binding?.swiperefresh?.setOnRefreshListener {
            binding?.swiperefresh?.isRefreshing = false
            viewModel.getProfile()
        }
    }

    private fun hideKeyboard(view: View){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}