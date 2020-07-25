package project.wgtech.imoge.explore.model

import android.os.Looper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import project.wgtech.imoge.util.ApiClient

class ExploreRepository {

    suspend fun getPhotosByKeywordEntity(
        api_unsplash_access : String,
        keyword: String
    ): Flowable<PhotosByKeywordEntity> {
        return withContext(Dispatchers.IO) {
            ApiClient.unsplashClient.create(ApiClient.IApiService::class.java)
                .getPhotosByKeyword(api_unsplash_access, keyword)
                .onBackpressureBuffer()
                .observeOn(AndroidSchedulers.from(Looper.getMainLooper()))
        }

    }
}