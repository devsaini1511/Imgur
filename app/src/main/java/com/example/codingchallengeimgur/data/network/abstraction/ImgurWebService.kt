package com.example.codingchallengeimgur.data.network.abstraction

import com.example.codingchallengeimgur.domain.response.SearchResponse
import com.example.codingchallengeimgur.domain.Resource

interface ImgurWebService {

    suspend fun getImgurResult(auth:String,query:String): Resource<SearchResponse>

}