package com.example.submissionintermediate.ui.detail_story

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.submissionintermediate.R
import com.example.submissionintermediate.data.remote.response.StoriesItem
import com.example.submissionintermediate.databinding.ActivityDetailStoryBinding
import com.example.submissionintermediate.ui.base.BaseActivity
import com.example.submissionintermediate.utils.TimeAgo
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class DetailStoryActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_activity_detail_story)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras
        val json = bundle?.getString("story")
        val story = Gson().fromJson(json, StoriesItem::class.java)


        binding.tvFullName.text = story.name
        val date = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault()
        ).parse(story.createdAt)
        if (date != null) {
            binding.tvCreatedAt.text = TimeAgo().getTimeAgo(date.time)
        }
        binding.tvDescription.text = story.description
        Glide.with(this).load(story.photoUrl).placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder).centerCrop().into(binding.imgPhoto)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}