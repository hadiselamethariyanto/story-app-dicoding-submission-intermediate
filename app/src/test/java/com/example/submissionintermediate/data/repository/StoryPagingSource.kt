package com.example.submissionintermediate.data.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.submissionintermediate.data.local.entities.StoryEntity

class StoryPagingSource : PagingSource<Int, StoryEntity>() {
    companion object {
        fun snapshot(items: List<StoryEntity>): PagingData<StoryEntity> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StoryEntity>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryEntity> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}