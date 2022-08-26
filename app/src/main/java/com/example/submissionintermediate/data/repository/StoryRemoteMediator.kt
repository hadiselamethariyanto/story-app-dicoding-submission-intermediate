package com.example.submissionintermediate.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.submissionintermediate.data.local.LocalDataSource
import com.example.submissionintermediate.data.local.entities.StoryEntity
import com.example.submissionintermediate.data.preferences.UserPreference
import com.example.submissionintermediate.data.remote.RemoteDataSource
import com.example.submissionintermediate.utils.DataMapper
import com.example.submissionintermediate.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val userPreference: UserPreference
) : RemoteMediator<Int, StoryEntity>() {

    private var page = 1

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, StoryEntity>
    ): MediatorResult {
        val nextPage = when (loadType) {
            LoadType.REFRESH -> INITIAL_PAGE_INDEX
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                if (state.lastItemOrNull() == null) {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }
                page++
                page
            }
        }
        wrapEspressoIdlingResource {
            return try {
                val token = "Bearer " + userPreference.getToken().first()
                val responseData =
                    remoteDataSource.getAllStoriesDirectly(token, nextPage, state.config.pageSize)
                val endOfPaginationReached = responseData.listStory.isEmpty()
                if (loadType == LoadType.REFRESH) {
                    localDataSource.deleteAllStories()
                }
                localDataSource.insertStories(DataMapper.mapStoryItemToStoryEntity(responseData.listStory))
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } catch (exception: Exception) {
                MediatorResult.Error(exception)
            }

        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

}