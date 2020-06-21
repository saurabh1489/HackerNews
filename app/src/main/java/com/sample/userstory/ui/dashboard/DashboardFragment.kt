package com.sample.userstory.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.sample.userstory.R
import com.sample.userstory.ui.common.BaseFragment
import com.sample.userstory.ui.common.ThemeViewModel
import com.sample.userstory.ui.dashboard.adapter.StoryAdapter
import com.sample.userstory.ui.vo.Story
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : BaseFragment() {

    private val dashboardViewModel: DashboardViewModel by viewModels {
        viewModelFactory
    }

    private val themeViewModel: ThemeViewModel by viewModels {
        viewModelFactory
    }

    private val adapter by lazy {
        StoryAdapter(themeViewModel.getTheme()) {
            navigateToDetailScreen(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        storyList.addItemDecoration(decoration)
        initAdapter()
        observeStories()
    }

    private fun observeStories() {
        dashboardViewModel.stories.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun initAdapter() {
        storyList.adapter = adapter
    }

    private fun navigateToDetailScreen(story: Story) {
        val action = DashboardFragmentDirections.actionDashboardToDetail(
            story.title ?: "",
            story.url ?: ""
        )
        findNavController().navigate(action)
    }

}