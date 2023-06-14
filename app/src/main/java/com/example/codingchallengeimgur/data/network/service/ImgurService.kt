package com.example.codingchallengeimgur.data.network.service

import com.example.codingchallengeimgur.domain.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ImgurService {
    @GET("gallery/search/top/week")
    suspend fun searchImages(
        @Header("Authorization") authorization: String,
        @Query("q") query: String
    ): Response<SearchResponse>
}