package com.example.codingchallengeimgur.data.network.implementation

import com.example.codingchallengeimgur.data.network.abstraction.ImgurWebService
import com.example.codingchallengeimgur.data.network.abstraction.DataSource
import com.example.codingchallengeimgur.data.network.service.ImgurService
import com.example.codingchallengeimgur.domain.response.SearchResponse
import com.example.codingchallengeimgur.domain.Resource

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImgurWebServiceImpl @Inject constructor(
    private val imgurService: ImgurService,
) : ImgurWebService, DataSource() {



    override suspend fun getImgurResult(auth:String,query: String): Resource<SearchResponse> =
        result{ imgurService.searchImages(auth,query)}


}




