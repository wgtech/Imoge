package project.wgtech.imoge.util

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Flowable
import okhttp3.OkHttpClient
import project.wgtech.imoge.explore.model.UnsplashJsonObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

object ApiClient {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
        })
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .callTimeout(1, TimeUnit.MINUTES)
        .build()

    val unsplashClient: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.unsplash.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    interface IUnsplashService {
        // https://unsplash.com/documentation#search-photos
        // https://api.unsplash.com/search/photos?query=minimal?client_id=CLIENT_ID
        @GET("/search/photos")
        fun photosByKeyword(
            @Query("client_id") accessKey: String,
            @Query("query") query: String,
            @Query("page") page: Int
        ) : Flowable<Response<UnsplashJsonObject>>
    }
}