package com.example.submissionintermediate.ui.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.example.submissionintermediate.R
import com.example.submissionintermediate.data.remote.network.ApiResponse
import com.example.submissionintermediate.data.remote.response.StoriesItem
import com.example.submissionintermediate.domain.IDicodingRepository
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class StackRemoteViewsFactory(private val mContext: Context, intent: Intent) :
    RemoteViewsService.RemoteViewsFactory, KoinComponent {

    private val mWidgetItems = ArrayList<StoriesItem>()
    private val repository by inject<IDicodingRepository>()
    private val mAppWidgetId: Int = intent.getIntExtra(
        AppWidgetManager.EXTRA_APPWIDGET_ID,
        AppWidgetManager.INVALID_APPWIDGET_ID
    )
    private val appWidgetManager = AppWidgetManager.getInstance(mContext)

    override fun onCreate() {
        mWidgetItems.clear()

        CoroutineScope(Dispatchers.IO).launch {
            repository.getStories().collectLatest { res ->
                when (res) {
                    is ApiResponse.Success -> {
                        val stories = res.data
                        mWidgetItems.addAll(stories.listStory)
                        appWidgetManager.notifyAppWidgetViewDataChanged(
                            mAppWidgetId,
                            R.id.stack_view
                        )
                    }
                    is ApiResponse.Error -> {
                        Log.d("UHT", "Error")
                    }
                }
            }
        }
    }

    override fun onDataSetChanged() {

    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)

        try {
            val bitmap = Glide.with(mContext)
                .asBitmap()
                .load(mWidgetItems[position].photoUrl)
                .submit(512, 512)
                .get()
            rv.setImageViewBitmap(R.id.image, bitmap)
        } catch (e: Exception) {
            Log.d("UHT", e.message.toString())
        }

        val gson = Gson().toJson(mWidgetItems[position])

        val extras = bundleOf(
            StoriesWidget.EXTRA_ITEM to gson
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.image, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

}