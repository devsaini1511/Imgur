package com.example.codingchallengeimgur.di


import com.example.codingchallengeimgur.data.network.abstraction.ImgurWebService
import com.example.codingchallengeimgur.data.network.implementation.ImgurWebServiceImpl
import com.example.codingchallengeimgur.data.network.service.ImgurService
import com.google.gson.Gson
import com.example.codingchallengeimgur.data.util.AuthInterceptor
import com.example.codingchallengeimgur.di.qualifiers.WithInterceptor
import com.example.codingchallengeimgur.domain.util.constants.NetworkConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesGson() = Gson()

    @Provides
    @Singleton
    @WithInterceptor
    fun providesOkHttp(
        authInterceptor: AuthInterceptor,
    ): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .readTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(2, TimeUnit.MINUTES)
            okHttpBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return okHttpBuilder.build()
    }


    @Singleton
    @Provides
    @WithInterceptor
    fun providesRetrofitInstance(
        @WithInterceptor okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(NetworkConstants.baseUrl)
        .build()

    @Singleton
    @Provides
    fun providesAuthService(
        @WithInterceptor retrofit: Retrofit,
    ): ImgurService = retrofit.create(ImgurService::class.java)

    @Singleton
    @Provides
    fun providesAuthWebService(
        authService: ImgurService,
    ): ImgurWebService = ImgurWebServiceImpl(authService)


}