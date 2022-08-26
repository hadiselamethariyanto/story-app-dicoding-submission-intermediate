package com.example.submissionintermediate.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.submissionintermediate.R
import com.example.submissionintermediate.data.local.entities.StoryEntity
import com.example.submissionintermediate.databinding.ActivityMainBinding
import com.example.submissionintermediate.ui.base.BaseActivity
import com.example.submissionintermediate.ui.language.ChangeLanguageActivity
import com.example.submissionintermediate.ui.login.LoginActivity
import com.example.submissionintermediate.ui.upload_story.UploadStoryActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMainAdapter()

        viewModel.getUser()
        viewModel.isLogin.observe(this) {
            if (!it) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

        binding.btnAddStory.setOnClickListener {
            val intent = Intent(this, UploadStoryActivity::class.java)
            launchAddNewStory.launch(intent)
        }

        viewModel.getPagingStories().observe(this, getStoriesObserver)
    }

    private val launchAddNewStory =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == UPLOAD_RESULT) {
                val message = it.data?.getStringExtra("message")
                mainAdapter.refresh()
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }

    private val getStoriesObserver = Observer<PagingData<StoryEntity>> { res ->
        mainAdapter.submitData(lifecycle, res)
    }

    private fun setLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvMain.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun setupMainAdapter() {
        mainAdapter = MainAdapter()
        mainAdapter.addLoadStateListener { loadState ->
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            val loadingState = loadState.refresh is LoadState.Loading

            setLoading(loadingState)

            if (errorState != null) {
                Toast.makeText(this, errorState.error.message, Toast.LENGTH_LONG).show()
            }

            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached) {
                if (mainAdapter.itemCount < 1) {
                    isEmptyData(true)
                } else {
                    isEmptyData(false)
                    binding.rvMain.smoothScrollToPosition(0)
                }
            }
        }
        binding.rvMain.adapter = mainAdapter
    }

    private fun isEmptyData(isEmpty: Boolean) {
        binding.placeholderEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.rvMain.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                logout()
                true
            }
            R.id.language -> {
                startActivity(Intent(this, ChangeLanguageActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        viewModel.logout()
    }

    companion object {
        const val UPLOAD_RESULT = 100
    }

}