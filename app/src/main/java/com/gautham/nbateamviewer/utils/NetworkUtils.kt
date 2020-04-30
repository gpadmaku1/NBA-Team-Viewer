package com.gautham.nbateamviewer.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import java.io.File
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

class NetworkUtils(applicationContext: Context) {

    companion object {
        private const val cacheSize: Long = 10 * 1024 * 1024
        private val TAG: String = NetworkUtils::class.java.simpleName
    }

    var httpCacheDirectory = File(applicationContext.cacheDir, "http-cache")
    private val cache = Cache(httpCacheDirectory, cacheSize)
    private val client =
        OkHttpClient.Builder().addNetworkInterceptor(CacheInterceptor()).cache(cache).build()
    private val requestBuilder = Request.Builder()

    fun getRequest(url: String): String? {
        try {
            val request = requestBuilder.url(url).build()
            val response = client.newCall(request).execute()
            response.apply {
                if (isSuccessful) {
                    body()?.string()?.let {
                        return it
                    }
                    Log.e(
                        TAG,
                        "Call to $url successful. Could not parse body. Response code: ${code()}"
                    )
                    return null
                } else {
                    Log.e(TAG, "Call to $url failed. Response code: ${code()}")
                    return null
                }
            }
        } catch (e: SocketTimeoutException) {
            Log.e(TAG, "Request timed out.")
            e.printStackTrace()
            getRequest(url)
        }
        return null
    }
}

class CacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        val cacheControl = CacheControl.Builder()
            .maxAge(15, TimeUnit.DAYS) // 15 days cache
            .build()

        return response.newBuilder()
            .removeHeader("Pragma")
            .removeHeader("Cache-Control")
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}