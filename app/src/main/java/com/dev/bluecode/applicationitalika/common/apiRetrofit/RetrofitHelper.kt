package com.dev.bluecode.applicationitalika.common.apiRetrofit

import com.dev.bluecode.applicationitalika.BuildConfig
import com.dev.bluecode.applicationitalika.common.uiUtils.Properties
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHelper {

    private val clientRetroFit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(Properties().baseUrl)
            .addConverterFactory(GsonConverterFactory.create(formatGson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(setInterceptorHttp().build())
            .build()

    val apiProductsServices: ProductsInterfaces
        get() = clientRetroFit.create(ProductsInterfaces::class.java)

    private fun setInterceptorHttp() : OkHttpClient.Builder{
        val builder = OkHttpClient()
            .newBuilder()
            .readTimeout(200, TimeUnit.SECONDS)
            .connectTimeout(200, TimeUnit.SECONDS)

        builder.addInterceptor( LoggingInterceptor.Builder()
            .tag("aaa")
            .setLevel(com.ihsanbal.logging.Level.BASIC)
            .log(Platform.INFO)
            .request("Request")
            .response("Response")
            .addHeader("version", BuildConfig.VERSION_NAME)
            .enableAndroidStudioV3LogsHack(true)
            .build())

        return builder

    }

    companion object {

        private val formatGson: Gson
            get() = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create()
    }
}