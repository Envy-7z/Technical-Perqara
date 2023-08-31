package com.wisnu.technicalassessmentlimaperqara.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.wisnu.technicalassessmentlimaperqara.data.database.MainDao
import com.wisnu.technicalassessmentlimaperqara.data.database.MainDatabase
import com.wisnu.technicalassessmentlimaperqara.data.models.FavEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetailRepository(application: Application) {

    private val mainDao: MainDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = MainDatabase.getDatabase(application)
        mainDao = db.userDao()
    }

    fun insert(user: FavEntity) {
        executorService.execute { mainDao.insert(user) }
    }

    fun getAllFav(): LiveData<List<FavEntity>> = mainDao.getAllFav()

    fun delete(user: FavEntity) {
        executorService.execute { mainDao.delete(user) }
    }

    fun getDataByUsername(username: String): LiveData<List<FavEntity>> =
        mainDao.getDataByUsername(username)
}