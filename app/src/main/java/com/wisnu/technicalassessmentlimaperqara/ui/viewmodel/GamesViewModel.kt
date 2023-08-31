package com.wisnu.technicalassessmentlimaperqara.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wisnu.technicalassessmentlimaperqara.data.models.Result
import com.wisnu.technicalassessmentlimaperqara.repositories.GamesRepository

class GamesViewModel (private val repository: GamesRepository) : ViewModel() {

    fun getGames(): LiveData<PagingData<Result>> {
        return  repository.getGames().cachedIn(viewModelScope)
    }

    fun getDetailGames(id:Int) =
        repository.getDetailGames(id)

}