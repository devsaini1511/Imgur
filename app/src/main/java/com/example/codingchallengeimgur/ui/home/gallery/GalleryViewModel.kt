package com.example.codingchallengeimgur.ui.home.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codingchallengeimgur.data.repository.ImgurRepository
import com.example.codingchallengeimgur.domain.response.SearchResponse
import com.example.codingchallengeimgur.domain.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val repository: ImgurRepository) : ViewModel() {

    private val _imageResponse: MutableSharedFlow<Resource<SearchResponse>> = MutableSharedFlow(0)
    val imageResponse = _imageResponse.asSharedFlow()

    private val queryText = MutableStateFlow("a")

    init {
        getImages()
    }

    fun getImages() {

        val handler = CoroutineExceptionHandler { _, throwable ->
            _imageResponse.tryEmit(Resource.Failure(throwable))
        }

        viewModelScope.launch(handler) {
            _imageResponse.emit(Resource.Loading)
            val response = repository.getImgurResult("", queryText.value)
            _imageResponse.emit(response)
        }
    }

    fun setQueryText(value:String){
        viewModelScope.launch {
            queryText.emit(value)
        }
    }
}