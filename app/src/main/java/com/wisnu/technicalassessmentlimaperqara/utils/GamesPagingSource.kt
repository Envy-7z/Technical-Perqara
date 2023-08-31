package com.wisnu.technicalassessmentlimaperqara.utils

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wisnu.technicalassessmentlimaperqara.data.api.ApiService
import com.wisnu.technicalassessmentlimaperqara.data.models.Result

class GamesPagingSource(
    private val apiService: ApiService,
) : PagingSource<Int, Result>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val hashMap = HashMap<String, Int>()
            hashMap["page"] = page
            hashMap["size"] = params.loadSize

            val responseData = apiService.getGames(hashMap).results
            Log.d("andrian", "$responseData")

            LoadResult.Page(
                data = responseData ?: emptyList(),
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}