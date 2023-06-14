package com.example.codingchallengeimgur.data.network.abstraction

import com.example.codingchallengeimgur.domain.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

abstract class DataSource {

    // A top level wrapper to call an API safely
    protected suspend inline fun <reified T> result(
        crossinline call: suspend () -> Response<T>,
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = call.invoke()

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null)
                        Resource.Success(body)
                    // FIXME: There might be a case in which response will be successful but data can be null
                    // See [#3075](https://github.com/square/retrofit/issues/3075) &
                    // [#1554](https://github.com/square/retrofit/issues/1554)
                    else Resource.Failure(Exception("No data found"))
                } else {
                    when (response.code()) {

                    }

                    Resource.Failure(Throwable(response.errorBody()?.getString()))
                }
            } catch (e: Exception) {
                Resource.Failure(e)
            }
        }.also {
            Timber.d("resource is $it")
        }
    }


    suspend fun ResponseBody.getString() = suspendCancellableCoroutine<String> {
        try {
            it.resume(string())
        } catch (e: Exception) {
            it.resumeWithException(e)
        } finally {
            close()
        }
        it.invokeOnCancellation { throwable ->
            Timber.e(throwable)
            close()
        }
    }
}