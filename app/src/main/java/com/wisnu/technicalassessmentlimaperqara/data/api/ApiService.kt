package com.wisnu.technicalassessmentlimaperqara.data.api

import com.wisnu.technicalassessmentlimaperqara.data.models.DetailGamesResponse
import com.wisnu.technicalassessmentlimaperqara.data.models.GamesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {


    @GET("games")
    suspend fun getGames(
        @QueryMap queryMap: Map<String, Int>,
        @Query("key") apikey : String? = "4ae781355c53487082181fb0330df6c5"
    ): GamesResponse


    @GET("games/{id}")
    suspend fun getDetailGames(
        @Path("id") id: Int,
        @Query("key") apikey : String? = "4ae781355c53487082181fb0330df6c5"
    ): DetailGamesResponse
}