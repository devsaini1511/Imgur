package com.example.codingchallengeimgur.data.repository

import com.example.codingchallengeimgur.data.network.abstraction.ImgurWebService
import com.example.codingchallengeimgur.domain.response.SearchResponse
import com.example.codingchallengeimgur.domain.Resource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ImgurRepository @Inject constructor(
    private val imgurWebService: ImgurWebService,
) {

    suspend fun getImgurResult(auth:String,query:String,): Resource<SearchResponse> {
        return imgurWebService.getImgurResult(auth,query)
    }
}