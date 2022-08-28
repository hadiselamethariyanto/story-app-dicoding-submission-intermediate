package com.example.submissionintermediate.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.submissionintermediate.databinding.ItemMainBinding
import com.example.submissionintermediate.ui.detail_story.DetailStoryActivity
import com.example.submissionintermediate.utils.TimeAgo
import com.google.gson.Gson
import java.text.SimpleDateFormat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.submissionintermediate.R
import com.example.submissionintermediate.data.local.entities.StoryEntity
import java.util.*

class MainAdapter : PagingDataAdapter<StoryEntity, MainAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(storiesItem: StoryEntity) {
            binding.tvFullName.text = storiesItem.name
            binding.tvDescription.text = storiesItem.description

            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(
                storiesItem.createdAt
            )
            if (date != null) {
                binding.tvCreatedAt.text = TimeAgo().getTimeAgo(date.time)
            }

            Glide.with(itemView.context).load(storiesItem.photoUrl)
                .placeholder(R.drawable.placeholder)
                .transform(CenterCrop(), RoundedCorners(24))
                .into(binding.imgPhoto)

            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("story", Gson().toJson(storiesItem))
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.imgPhoto, "photo"),
                        Pair(binding.imgUser, "user"),
                        Pair(binding.tvFullName, "full_name"),
                    )
                val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                intent.putExtras(bundle)
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.binding(item)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryEntity>() {

            override fun areItemsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}