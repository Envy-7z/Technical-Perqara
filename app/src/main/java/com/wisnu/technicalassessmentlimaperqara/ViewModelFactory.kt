package com.wisnu.technicalassessmentlimaperqara

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wisnu.technicalassessmentlimaperqara.di.MainInjection
import com.wisnu.technicalassessmentlimaperqara.repositories.GamesRepository
import com.wisnu.technicalassessmentlimaperqara.ui.viewmodel.GamesViewModel

class ViewModelFactory(private val repository: GamesRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(GamesViewModel::class.java) -> GamesViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.simpleName)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(MainInjection.provideRepository(context))
            }.also { instance = it }
        }
    }
}