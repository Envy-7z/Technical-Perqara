package com.wisnu.technicalassessmentlimaperqara.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wisnu.technicalassessmentlimaperqara.data.models.FavEntity
import com.wisnu.technicalassessmentlimaperqara.repositories.DetailRepository

class DetailViewModel(application: Application) : ViewModel() {

    private val detailRepo: DetailRepository = DetailRepository(application)

    fun insertData(users: FavEntity) {
        detailRepo.insert(users)
    }

    fun deleteData(user: FavEntity) {
        detailRepo.delete(user)
    }

    fun getDataByUsername(username: String) = detailRepo.getDataByUsername(username)

    private val tempLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = tempLoading

    fun getAllDataFav() = detailRepo.getAllFav()

    class ViewModelFactory(private val application: Application) :
        ViewModelProvider.NewInstanceFactory() {
        companion object {
            @Volatile
            private var INSTANCE: ViewModelFactory? = null

            @JvmStatic
            fun getInstance(application: Application ): ViewModelFactory {
                if (INSTANCE == null) {
                    synchronized(ViewModelFactory::class.java) {
                        INSTANCE = ViewModelFactory(application)
                    }
                }
                return INSTANCE as ViewModelFactory
            }
        }

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }


}