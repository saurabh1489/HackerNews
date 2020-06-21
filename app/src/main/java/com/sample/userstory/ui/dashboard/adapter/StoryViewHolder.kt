package com.sample.userstory.ui.dashboard.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sample.userstory.ui.vo.Story
import kotlinx.android.synthetic.main.item_story.view.*

class StoryViewHolder(
    private val view: View,
    private val clickAction: (Story) -> Unit
) : RecyclerView.ViewHolder(view) {

    fun bind(story: Story) {
        view.title.text = story.title
        view.setOnClickListener {
            clickAction(story)
        }
    }

}