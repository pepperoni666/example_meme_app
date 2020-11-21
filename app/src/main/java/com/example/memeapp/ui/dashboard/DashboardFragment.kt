package com.example.memeapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.memeapp.R
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardViewModel.text.observe({ lifecycle }, Observer {
            text_dashboard.text = it
        })
    }
}