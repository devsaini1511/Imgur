package com.example.codingchallengeimgur.di

import com.example.codingchallengeimgur.data.network.abstraction.ImgurWebService
import com.example.codingchallengeimgur.data.repository.ImgurRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {


    @Provides
    @ViewModelScoped
    fun providesAuthRepository(
        authWebService: ImgurWebService,
    ) = ImgurRepository(authWebService)


}