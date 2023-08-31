package com.wisnu.technicalassessmentlimaperqara.di

import android.content.Context
import com.wisnu.technicalassessmentlimaperqara.data.api.ApiConfig
import com.wisnu.technicalassessmentlimaperqara.repositories.GamesRepository


object MainInjection {
    fun provideRepository(context: Context): GamesRepository {
        val apiService = ApiConfig.getApiClient()
        return GamesRepository(apiService)
    }
}