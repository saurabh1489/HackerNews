package com.sample.userstory.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.sample.userstory.R
import com.sample.userstory.databinding.FragmentDetailBinding
import com.sample.userstory.databinding.FragmentLoginBinding
import com.sample.userstory.ui.common.BaseFragment
import com.sample.userstory.ui.dashboard.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : BaseFragment() {

    private val detailViewModel: DetailViewModel by viewModels {
        viewModelFactory
    }

    val args: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        binding.detailViewModel = detailViewModel
        title.text = args.title
        detailViewModel.loadUrl(args.storyUrl)
    }

}