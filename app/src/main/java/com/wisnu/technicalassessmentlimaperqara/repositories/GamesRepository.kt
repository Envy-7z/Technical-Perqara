package com.wisnu.technicalassessmentlimaperqara.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.wisnu.technicalassessmentlimaperqara.data.api.ApiService
import com.wisnu.technicalassessmentlimaperqara.data.models.DetailGamesResponse
import com.wisnu.technicalassessmentlimaperqara.data.models.Result
import com.wisnu.technicalassessmentlimaperqara.data.models.Results
import com.wisnu.technicalassessmentlimaperqara.utils.GamesPagingSource
import java.lang.Exception

class GamesRepository(private val apiService: ApiService) {

    fun getGames(): LiveData<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                GamesPagingSource(apiService)
            }
        ).liveData
    }

    fun getDetailGames(id : Int): LiveData<Results<DetailGamesResponse>> = liveData {
        emit(Results.Loading)
        try {
            val response = apiService.getDetailGames(id)
            emit(Results.Success(response))
        } catch (e: Exception) {
            Log.d("loc", e.message.toString())
            emit(Results.Error(e.message.toString()))
        }
    }
}