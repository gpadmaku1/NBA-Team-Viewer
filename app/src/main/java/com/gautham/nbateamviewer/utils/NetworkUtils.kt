package com.gautham.nbateamviewer.utils

import android.content.Context
import android.util.Log
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

class NetworkUtils(applicationContext: Context) {

    companion object {
        private const val cacheSize: Long = 10 * 1024 * 1024
        private val TAG: String = NetworkUtils::class.java.simpleName
    }

    private val cache = Cache(applicationContext.cacheDir, cacheSize)
    private val client = OkHttpClient.Builder().cache(cache).build()
    private val requestBuilder = Request.Builder()

    fun getRequest(url: String): String? {
        try {
            val request = requestBuilder.url(url).cacheControl(
                CacheControl.Builder().maxStale(
                    14,
                    TimeUnit.DAYS
                ).noCache().build()
            ).build()
            val response = client.newCall(request).execute()
            val cacheResponse = response.cacheResponse()
            val networkResponse = response.networkResponse()
            //always returns null
            Log.d(TAG, "cacheResponse: ${cacheResponse.toString()}")
            //always has the response
            Log.d(TAG, "networkResponse: ${networkResponse.toString()}")
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