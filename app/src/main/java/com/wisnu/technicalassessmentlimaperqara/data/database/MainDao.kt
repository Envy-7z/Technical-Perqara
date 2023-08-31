package com.wisnu.technicalassessmentlimaperqara.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wisnu.technicalassessmentlimaperqara.data.models.FavEntity

@Dao
interface MainDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: FavEntity)

    @Delete
    fun delete(user: FavEntity)

    @Query("SELECT * from MainTable ORDER BY name ASC")
    fun getAllFav(): LiveData<List<FavEntity>>

    @Query("SELECT * FROM MainTable WHERE name = :username")
    fun getDataByUsername(username: String): LiveData<List<FavEntity>>
}