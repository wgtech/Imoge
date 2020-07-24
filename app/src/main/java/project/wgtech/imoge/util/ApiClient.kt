package project.wgtech.imoge.util

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Flowable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ApiClient {

    val unsplashClient: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.unsplash.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    interface IApiService {
        @GET("/search/photos")
        fun getPhotosByKeyword(
            @Query("client_id") accessKey: String, @Query("query") query: String
        ) : Flowable<PhotosByKeywordEntity>
    }
}