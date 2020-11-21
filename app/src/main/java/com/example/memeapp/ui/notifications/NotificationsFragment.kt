package com.example.memeapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.memeapp.R
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {

    private val notificationsViewModel: NotificationsViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationsViewModel.text.observe({ lifecycle }, Observer {
            text_notifications.text = it
        })
    }
}