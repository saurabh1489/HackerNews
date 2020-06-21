package com.sample.userstory.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.sample.userstory.R
import com.sample.userstory.data.repository.Theme
import com.sample.userstory.databinding.ItemStoryBinding
import com.sample.userstory.ui.vo.Story

class StoryAdapter(val theme: Theme, private val clickAction: (Story) -> Unit) :
    PagedListAdapter<Story, StoryViewHolder>(STORY_COMPARATOR) {

    companion object {
        private val STORY_COMPARATOR = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = DataBindingUtil.inflate<ItemStoryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_story,
            parent,
            false
        )
        binding.theme = theme
        return StoryViewHolder(binding.root, clickAction)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = getItem(position)
        story?.run {
            holder.bind(story)
        }
    }
}